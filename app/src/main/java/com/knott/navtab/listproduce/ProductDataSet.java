package com.knott.navtab.listproduce;

public interface ProductDataSet {

    int size();

    Product get(int position);

    long getId(int position);

}
