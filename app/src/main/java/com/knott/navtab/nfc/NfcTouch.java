package com.knott.navtab.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.knott.navtab.MainActivity;
import com.knott.navtab.R;
import com.knott.navtab.fragment.TabMenuFragment;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class NfcTouch extends AppCompatActivity {

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
        }else {
            Intent intent = getIntent();
            String action = intent.getAction();

            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
//            Toast.makeText(this,
//                    "onResume() - ACTION_TAG_DISCOVERED",
//                    Toast.LENGTH_SHORT).show();

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String tagInfo = new String();
                if(tag == null){
//                textViewInfo.setText("tag == null");
                }else{

                    byte[] tagId = tag.getId();

                    for(int i=0; i<tagId.length; i++){
                        tagInfo += Integer.toHexString(tagId[i] & 0xFF);
                    }

                    Utinity.table_id = tagInfo;
                    //Toast.makeText(getApplicationContext(), String.valueOf(Utinity.user_id), Toast.LENGTH_LONG).show();
                    RequestParams params = new RequestParams();
                    params.put("TableID", Utinity.table_id);
                    params.put("customerID",String.valueOf(Utinity.user_id));
                    invokeWS(params);

                }
            }else{
//            Toast.makeText(this,
//                    "onResume() : " + action,
//                    Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Intent intent = getIntent();
//        String action = intent.getAction();
//
//        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
////            Toast.makeText(this,
////                    "onResume() - ACTION_TAG_DISCOVERED",
////                    Toast.LENGTH_SHORT).show();
//
//            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            String tagInfo = new String();
//            if(tag == null){
////                textViewInfo.setText("tag == null");
//            }else{
//
//                byte[] tagId = tag.getId();
//
//                for(int i=0; i<tagId.length; i++){
//                    tagInfo += Integer.toHexString(tagId[i] & 0xFF);
//                }
//
//                Utinity.table_id = tagInfo;
//                //Toast.makeText(getApplicationContext(), String.valueOf(Utinity.user_id), Toast.LENGTH_LONG).show();
//                RequestParams params = new RequestParams();
//                params.put("TableID", Utinity.table_id);
//                params.put("customerID",String.valueOf(Utinity.user_id));
//                invokeWS(params);
//
//            }
//        }else{
////            Toast.makeText(this,
////                    "onResume() : " + action,
////                    Toast.LENGTH_SHORT).show();
//        }
//
//    }


    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
//        textViewInfo.setText(params.toString());
        client.get(Utinity.url+"table/dotable",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

//                Toast.makeText(getApplicationContext(), response , Toast.LENGTH_LONG).show();
//                textViewInfo.setText(response.toString());
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        Utinity.NFC = false;
//                        Toast.makeText(getApplicationContext(), ""+obj.getInt("number"), Toast.LENGTH_LONG).show();
//                        FragmentManager myFragmentManager = getSupportFragmentManager();
//                        FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
//                        myFragmentTransaction.replace(R.id.content_view, new TabMenuFragment()).commit();
                        finish();
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getApplicationContext(), obj.getString("Busy"), Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

//                Intent  orderIneger = new Intent (NfcTouch.this,MainActivity.class);
//                startActivity(orderIneger);
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end ", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

