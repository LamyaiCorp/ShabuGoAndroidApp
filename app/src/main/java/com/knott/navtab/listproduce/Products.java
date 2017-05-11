package com.knott.navtab.listproduce;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KNOTT on 5/5/2560.
 */

public class Products implements ProductDataSet,Serializable {

    private final List<Product> productList;

    public Products(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int size() {
        return productList.size();
    }

    @Override
    public Product get(int position) {
        return productList.get(position);
    }

    @Override
    public long getId(int position) {
        return position;
    }

    public  List<Product> getData(){
//        Log.d("ProductList",productList.toString());
        return productList;
    }

    public  void setZero(){

    }

    public void removeOneFrom(Product product) {
        int i = productList.indexOf(product);
        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }
        if (product.quantity > 0){
            Product updatedProduct = new Product(product.id, product.name, product.price, (product.quantity - 1), product.img);
            productList.remove(product);
            productList.add(i, updatedProduct);
        }

    }

    public void addOneTo(Product product) {
        int i = productList.indexOf(product);
        if (i == -1) {
            throw new IndexOutOfBoundsException();
        }
        Product updatedProduct = new Product(product.id, product.name, product.price, (product.quantity + 1), product.img);
        productList.remove(product);
        productList.add(i, updatedProduct);

        for(int j=0; j<productList.size(); j++ ){
            if(productList.get(j).getQuantity()>0)
                Log.d("ProductList",productList.get(j).getName());
        }

    }


}

