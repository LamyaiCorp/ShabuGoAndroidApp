package com.knott.navtab.fragement_home;

import com.knott.navtab.fragment_cart.CartItem;
import com.knott.navtab.listproduce.Product;

/**
 * Created by KNOTT on 12/5/2560.
 */

public interface PromotinDataSet {
    int size();

    Promotion get(int position);

    long getId(int position);
}
