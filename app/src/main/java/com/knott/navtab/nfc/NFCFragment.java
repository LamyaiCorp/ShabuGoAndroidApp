package com.knott.navtab.nfc;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.knott.navtab.R;
import com.knott.navtab.fragment.TabMenuFragment;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class NFCFragment extends Fragment implements NFCcallback.AccountCallback {

    public static final String TAG = "CardReaderFragment";
    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public NFCcallback nfCcallback;

    /** Called when sample is created. Displays generic UI with welcome text. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootViw = inflater.inflate(R.layout.fragment_nfc, container, false);

        if (rootViw != null) {
            nfCcallback = new NFCcallback(this);
            enableReaderMode();
        }

//        Button nfc_clivk = (Button) rootViw.findViewById(R.id.button_nfc);
//        nfc_clivk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utinity.NFC = false;
//
//                RequestParams params = new RequestParams();
//                params.put("TableID",Utinity.table_id);
//                params.put("customerID",String.valueOf(Utinity.user_id));
//                invokeWS(params);
//
////                FragmentManager myFragmentManager = getActivity().getSupportFragmentManager();
////                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
////                myFragmentTransaction.replace(R.id.content_view, new TabMenuFragment()).commit();
//            }
//        });
        return rootViw;
    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.enableReaderMode(activity, nfCcallback, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onAccountReceived(final String account) {

        RequestParams params = new RequestParams();
        params.put("TableID", account);
        params.put("customerID",String.valueOf(Utinity.user_id));
        invokeWS(params);
    }

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

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        Utinity.NFC = false;
                        Utinity.Oder_id = obj.getInt("orderID");
                        Utinity.table_number = obj.getInt("number");
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
                    Toast.makeText(getActivity(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getActivity(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getActivity(), "Something went wrong at server end ", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

