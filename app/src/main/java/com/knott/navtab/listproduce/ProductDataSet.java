package com.knott.navtab.listproduce;

import com.knott.navtab.fragement_home.Promotion;

public interface ProductDataSet {

    int size();

    Product get(int position);

    long getId(int position);

}
