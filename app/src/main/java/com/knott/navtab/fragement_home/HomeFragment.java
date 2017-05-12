package com.knott.navtab.fragement_home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.knott.navtab.R;
import com.knott.navtab.fragment_cart.CartItemAdapter;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.knott.navtab.fragment_cart.CartFragment.cartitems;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ListView homeListview;
    Promotions pormotions;
    private JSONArray obj;
    private JSONObject jsondata;
    private PromotionAdapter promotionAdapter;
    ListView homeListView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);


        invokeWS();

        return rootview;
    }




    private void invokeWS() {


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utinity.url + "menuFood" + "/" + "promotion", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d("TAG ===== ",response.toString());

                // JSON Object
                try {
                    obj = new JSONArray(response);

                    if(obj != null){

                        Log.d("TAG ===== ","not nullllllll");
                        pormotions = createInitialProductList(obj);
                        Log.d("TAG ===== ",String.valueOf(pormotions.size()));

                        if(pormotions.size()>0){
                            Log.d("TAG ===== ","size>0");
                            promotionAdapter = new PromotionAdapter(pormotions,getActivity().getLayoutInflater());
                            homeListView = (ListView) getActivity().findViewById(R.id.listview_home);
                            homeListView.setAdapter(promotionAdapter);

                        }
//
//                        homeListview = (ListView) rootview.findViewById(R.id.listview_home);
//                        PromotionAdapter promotionAdapter = new PromotionAdapter(pormotions,getActivity().getLayoutInflater());
//                        homeListview.setAdapter(promotionAdapter);
//                        homeListview.setDivider(null);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                // When Http response code is '404'
                if (statusCode == 404) {

                }
                // When Http response code is '500'
                else if (statusCode == 500) {

                }
                // When Http response code other than 404, 500
                else {

                }
            }

            @Override
            public void onRetry() {
                super.onRetry();
                Log.d("TAG ===== ","onRetrydddddd");
                homeListView.setAdapter(promotionAdapter);

            }
        });


    }

    private Promotions createInitialProductList(JSONArray obj) throws JSONException {


        ArrayList<Promotion> arrayList = new ArrayList();
        for(int i = 0; i< obj.length(); i++){
            jsondata = obj.getJSONObject(i);
            arrayList.add(
                    new Promotion(Utinity.urlImg + String.valueOf(jsondata.get("img"))+"&folder=promotion",
                            String.valueOf(jsondata.get("mess"))
                    )
            );
            Log.d("TAG ===== ",arrayList.get(i).getUrl()+" : "+ arrayList.get(i).getContent());
        }

//        if( pormotions == null){
            pormotions = new Promotions(arrayList);

//        }


        return  pormotions;
    }

}
