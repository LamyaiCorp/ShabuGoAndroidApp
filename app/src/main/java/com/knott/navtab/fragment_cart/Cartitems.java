package com.knott.navtab.fragment_cart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KNOTT on 12/5/2560.
 */

public class Cartitems implements CartitemSet,Serializable {

    private final List<CartItem> cartItemList;

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public Cartitems(List<CartItem> productList) {
        this.cartItemList = productList;
    }

    @Override
    public int size() {
        return cartItemList.size();
    }

    @Override
    public CartItem get(int position) {
        return cartItemList.get(position);
    }

    @Override
    public long getId(int position) {
        return position;
    }
}
