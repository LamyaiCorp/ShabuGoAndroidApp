package com.knott.navtab.nfc;


import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.knott.navtab.R;
import com.knott.navtab.fragment.TabMenuFragment;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;


public class NFCFragment extends Fragment {

    private NfcAdapter nfcAdapter;

    public NFCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootViw = inflater.inflate(R.layout.fragment_nfc, container, false);


//
//        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
//        if(nfcAdapter == null){
//            Toast.makeText(getActivity(),"NFC NOT supported on this devices!",Toast.LENGTH_LONG).show();
//            getActivity().finish();

//        }else if(!nfcAdapter.isEnabled()){
//            Toast.makeText(getActivity(),"NFC NOT Enabled!", Toast.LENGTH_LONG).show();
//            getActivity().finish();
//        }

        Button nfc_clivk = (Button) rootViw.findViewById(R.id.button_nfc);
        nfc_clivk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utinity.NFC = false;

                RequestParams params = new RequestParams();
                params.put("id", "");
                params.put("customerID",Utinity.user_id);
                invokeWS(params);

//                FragmentManager myFragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
//                myFragmentTransaction.replace(R.id.content_view, new TabMenuFragment()).commit();
            }
        });
        return rootViw;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        Intent intent = getActivity().getIntent();
//        String action = intent.getAction();
//
//        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
//            Toast.makeText(getActivity(),
//                    "onResume() - ACTION_TAG_DISCOVERED",
//                    Toast.LENGTH_SHORT).show();
//
//            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            String tagInfo = new String();
//            if(tag == null){
//                textViewInfo.setText("tag == null");
//            }else{
//
//                byte[] tagId = tag.getId();
//
//                for(int i=0; i<tagId.length; i++){
//                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
//                }
//
//                RequestParams params = new RequestParams();
//                params.put("id", tagInfo);
//                params.put("customerID",Utinity.user_id);
//                invokeWS(params);
//
//            }
//        }else{
//            Toast.makeText(getActivity(),
//                    "onResume() : " + action,
//                    Toast.LENGTH_SHORT).show();
//        }
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
        client.get(Utinity.url+"useraccount/table/dotable",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
//                textViewInfo.setText(response.toString());
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        Utinity.NFC = false;
                        FragmentManager myFragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                        myFragmentTransaction.replace(R.id.content_view, new TabMenuFragment()).commit();
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), obj.getString("Busy"), Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity().getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong at server end ", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

