package com.knott.navtab.fragment_cart;

/**
 * Created by KNOTT on 12/5/2560.
 */

public interface CartitemSet {

    int size();

    CartItem get(int position);

    long getId(int position);
}
