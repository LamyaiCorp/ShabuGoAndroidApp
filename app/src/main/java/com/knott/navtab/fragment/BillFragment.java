package com.knott.navtab.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.knott.navtab.Main2Activity;
import com.knott.navtab.R;
import com.knott.navtab.loing.LoginActivity;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;


public class BillFragment extends Fragment{

    public BillFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_bill, container, false);

        RequestParams params = new RequestParams();
        params.put("orderID", String.valueOf(Utinity.Oder_id));

        Log.d("Param ===== ", params.toString());
        invokeWS(params);


        return rootview;
    }


    public void invokeWS(RequestParams params){
        Log.d("TAG","invoke bill");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utinity.url+"order/gettotal",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true

                    Log.d("BILL",obj.toString());


                    TextView tb_num = (TextView) getActivity().findViewById(R.id.textView12);
                    TextView od_num = (TextView) getActivity().findViewById(R.id.textView14);
                    TextView date = (TextView) getActivity().findViewById(R.id.textView16);
                    TextView total_price = (TextView) getActivity().findViewById(R.id.textView18);

                    tb_num.setText(String.valueOf(Utinity.table_number));
                    od_num.setText(String.valueOf(Utinity.Oder_id));
                    date.setText( obj.getString("date"));
                    total_price.setText( String.valueOf(obj.getInt("total")));

                } catch (JSONException e) {

                    e.printStackTrace();

                }
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
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
