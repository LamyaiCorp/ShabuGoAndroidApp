package com.knott.navtab.fragment_cart;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.knott.navtab.R;
import com.knott.navtab.listproduce.Product;
import com.knott.navtab.listproduce.ProductClickListener;
import com.knott.navtab.listproduce.Products;
import com.knott.navtab.listproduce.ProductsAdapter;
import com.knott.navtab.unity.Utinity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    JSONArray obj ;
    JSONObject jsondata;
    public static Cartitems cartitems;
    ListView cartListView;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_cart, container, false);

        RequestParams params = new RequestParams();
        params.put("orderID", "34");
        invokeWS(params);

        return rootview;
    }

    private void invokeWS(RequestParams params) {


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utinity.url + "order" + "/" + "getorder",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d("TAG ===== ",response.toString());

                // JSON Object
                try {
                    obj = new JSONArray(response);

                    if(obj != null){
                        cartitems = createInitialProductList(obj);
                        CartItemAdapter CartItemAdapter = new CartItemAdapter(cartitems,getActivity().getLayoutInflater());
                        cartListView = (ListView) getActivity().findViewById(R.id.list_cart_item);
                        cartListView.setAdapter(CartItemAdapter);
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
//            products.removeOneFrom(product);
//            productsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPlusClick(Product product) {
//            products.addOneTo(product);
//            productsAdapter.notifyDataSetChanged();
        }
    };


    private Cartitems createInitialProductList(JSONArray obj) throws JSONException {


        ArrayList<CartItem> arrayList = new ArrayList();
        for(int i = 0; i< obj.length(); i++){
            jsondata = obj.getJSONObject(i);
            arrayList.add(
                    new CartItem(
                            String.valueOf(jsondata.get("name")),
                            Integer.valueOf(jsondata.getInt("price")),
                            Integer.valueOf(jsondata.getInt("quantity"))
                    )
            );
        }

        if(cartitems == null){
            cartitems = new Cartitems(arrayList);

        }


        return  cartitems;
    }

    @Override
    public void onStart() {
        super.onStart();
//        invokeWS();
    }

    @Override
    public void onResume() {
        super.onResume();
//        invokeWS();
    }



}
