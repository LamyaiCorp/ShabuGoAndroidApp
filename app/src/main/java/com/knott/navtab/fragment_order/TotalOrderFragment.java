package com.knott.navtab.fragment_order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.knott.navtab.R;
import com.knott.navtab.fragment_munu.DrinkFragment;
import com.knott.navtab.fragment_munu.FreshFoodFragment;
import com.knott.navtab.fragment_munu.VegetableFragment;
import com.knott.navtab.listproduce.Product;
import com.knott.navtab.listproduce.ProductClickListener;
import com.knott.navtab.listproduce.Products;
import com.knott.navtab.listproduce.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TotalOrderFragment extends Fragment {


    private List<Product> list1 ,list2,list3;
    int totalPrice = 0;
    private Products productsList;

    public TotalOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_total_order, container, false);

        if(FreshFoodFragment.products.getData() != null){
            list1 = FreshFoodFragment.products.getData();
        }
        if(VegetableFragment.products.getData() != null){
            list2 = VegetableFragment.products.getData();
        }
        if(DrinkFragment.products.getData() != null){
            list3 = DrinkFragment.products.getData();
        }


        ArrayList<Product> OrderTatal = new ArrayList();

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


}
