package com.knott.navtab.fragment_cart;

import java.io.Serializable;

/**
 * Created by KNOTT on 12/5/2560.
 */

public class CartItem implements Serializable {

    final String name;
    final  int totalPriceItem;
    final  int totalQuntityItem;

    public CartItem(String name, int price, int totalQuntityItem) {
        this.name = name;
        this.totalPriceItem = price;
        this.totalQuntityItem = totalQuntityItem;
    }

    public String getName() {
        return name;
    }

    public int getTotalPriceItem() {
        return totalPriceItem;
    }

    public int getTotalQuntityItem() {
        return totalQuntityItem;
    }
}
