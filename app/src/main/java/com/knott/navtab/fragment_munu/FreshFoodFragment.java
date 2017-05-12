package com.knott.navtab.fragment_munu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.knott.navtab.R;
import com.knott.navtab.listproduce.Product;
import com.knott.navtab.listproduce.ProductClickListener;
import com.knott.navtab.listproduce.Products;
import com.knott.navtab.listproduce.ProductsAdapter;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FreshFoodFragment extends Fragment {

    ListView productsListView;
    JSONArray obj ;
    JSONObject jsondata;
    public static Products products;
    ProductsAdapter productsAdapter;


    public FreshFoodFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fresh_food, container, false);

        invokeWS();

        return rootView;
    }


    private void invokeWS() {


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utinity.url + "menuFood" + "/" + "getFreshFood", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d("TAG ===== ","invokeWS");
                try {

                    // JSON Object
                    obj = new JSONArray(response);

                    if(obj != null){

                        products = createInitialProductList(obj);
                         if(products.getData() != null){
                             productsAdapter = new ProductsAdapter(products, productClickListener, getActivity().getLayoutInflater());
                             productsListView = (ListView) getActivity().findViewById(R.id.listview_f1);
                             productsListView.setAdapter(productsAdapter);
                         }
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
        });

    }

    private final ProductClickListener productClickListener = new ProductClickListener() {
        @Override
        public void onMinusClick(Product product) {
            products.removeOneFrom(product);
            productsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPlusClick(Product product) {
            products.addOneTo(product);
            productsAdapter.notifyDataSetChanged();
        }
    };


    private Products createInitialProductList(JSONArray obj) throws JSONException {

        Log.d("lifeCecle ==== ","createInitialProductList");
        ArrayList<Product> arrayList = new ArrayList();

        for(int i = 0; i< obj.length(); i++){
            jsondata = obj.getJSONObject(i);
            arrayList.add(
                    new Product(Integer.valueOf((Integer) jsondata.get("id")),
                            String.valueOf(jsondata.get("name")),
                            Integer.valueOf((Integer) jsondata.get("price")) ,
                            0,
                            Utinity.urlImg + String.valueOf(jsondata.get("img"))+"&folder=fresh")
            );
        }


      if(products == null){
        products = new Products(arrayList);

      }

        return  products;
    }

    @Override
    public void onStart() {
        super.onStart();
//        invokeWS();
    }

    @Override
    public void onResume() {
        super.onResume();
        invokeWS();

//        Products products = new Products(alllist);

    }

    @Override
    public void onPause() {
        super.onPause();

//        alllist = products.getData();

        Log.d("lifeCecle ==== ","onPause");


    }

}
