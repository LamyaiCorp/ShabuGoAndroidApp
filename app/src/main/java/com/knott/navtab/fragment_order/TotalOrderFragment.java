package com.knott.navtab.fragment_order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.knott.navtab.R;
import com.knott.navtab.fragment.TabMenuFragment;
import com.knott.navtab.fragment_munu.DrinkFragment;
import com.knott.navtab.fragment_munu.FreshFoodFragment;
import com.knott.navtab.fragment_munu.VegetableFragment;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TotalOrderFragment extends Fragment {

    View rootview;
    private List<Product> list1 ,list2,list3;
    int totalPrice = 0;
    private Products productsList;
    ArrayList<Product> OrderTatal;

    public TotalOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_total_order, container, false);

        Button btn = (Button) rootview.findViewById(R.id.but_confirm_order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","click");


                JSONArray jsonArray = new JSONArray();

                ArrayList<JSONObject> MyArrJson = new ArrayList<JSONObject>();
                for (int i = 0 ;i < OrderTatal.size();i++ ){
                    JSONObject jsonObject = new JSONObject();
                         Log.d("TalatOrder", Utinity.user_id +" : "+ OrderTatal.get(i).getId()+" : "+ OrderTatal.get(i).getQuantity());


                    try {
                        jsonObject.put("id", OrderTatal.get(i).getId())
                            .put("sumprice", ((OrderTatal.get(i).getPrice()*(OrderTatal.get(i).getQuantity()))))
                                .put("quantity", OrderTatal.get(i).getQuantity());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jsonArray.put(jsonObject);

                }


                RequestParams params = new RequestParams();
                params.put("orderID",Utinity.Oder_id);
                params.put("FoodObj",jsonArray.toString());

                Log.d("Param ===== ", params.toString());
                invokeWS(params);

            }
        });


        if(FreshFoodFragment.products != null){
            list1 = FreshFoodFragment.products.getData();
        }
        if(VegetableFragment.products != null){
            list2 = VegetableFragment.products.getData();
        }
        if(DrinkFragment.products != null){
            list3 = DrinkFragment.products.getData();
        }


        OrderTatal = new ArrayList();

        if (list1 != null){
            for (int i =0 ;i < list1.size();i++){
                Log.d("TAG", String.valueOf(list1.get(i).getName()) +" : "+String.valueOf(list1.get(i).getQuantity()));

                if(list1.get(i).getQuantity() > 0){

                    OrderTatal.add(new Product(Integer.valueOf(list1.get(i).getId()),String.valueOf(list1.get(i).getName()),Integer.valueOf(list1.get(i).getPrice()),Integer.valueOf(list1.get(i).getQuantity()),String.valueOf(list1.get(i).getImg()))

                    );

                    totalPrice = totalPrice + (list1.get(i).getPrice()*list1.get(i).getQuantity());

                }

            }
        }

        if(list2 != null){
            for (int i =0 ;i < list2.size();i++){
                Log.d("TAG", String.valueOf(list2.get(i).getName()) +" : "+String.valueOf(list2.get(i).getQuantity()));

                if(list2.get(i).getQuantity() > 0){

                    OrderTatal.add(new Product(Integer.valueOf(list2.get(i).getId()),String.valueOf(list2.get(i).getName()),Integer.valueOf(list2.get(i).getPrice()),Integer.valueOf(list2.get(i).getQuantity()),String.valueOf(list2.get(i).getImg()))

                    );

                    totalPrice = totalPrice + (list2.get(i).getPrice()*list2.get(i).getQuantity());

                }

            }
        }

        if (list3 != null){

            for (int i =0 ;i < list3.size();i++){
                Log.d("TAG", String.valueOf(list3.get(i).getName()) +" : "+String.valueOf(list3.get(i).getQuantity()));

                if(list3.get(i).getQuantity() > 0){

                    OrderTatal.add(new Product(Integer.valueOf(list3.get(i).getId()),String.valueOf(list2.get(i).getName()),Integer.valueOf(list3.get(i).getPrice()),Integer.valueOf(list3.get(i).getQuantity()),String.valueOf(list3.get(i).getImg()))

                    );

                    totalPrice = totalPrice + (list3.get(i).getPrice()*list3.get(i).getQuantity());

                }

            }

        }

//

        productsList = new Products(OrderTatal);

        ProductsAdapter productsAdapter = new ProductsAdapter(productsList, productClickListener, getActivity().getLayoutInflater());
        ListView productsListView = (ListView) rootview.findViewById(R.id.list_oderdetail);
        productsListView.setAdapter(productsAdapter);

        TextView textView_price = (TextView) rootview.findViewById(R.id.text_total_price);
        textView_price.setText(String.valueOf(totalPrice));



        return  rootview;
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

    public void invokeWS(RequestParams params){

        Log.d("Tag ---- ",params.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Utinity.url+"order/addorder",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){


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
