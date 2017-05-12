package com.knott.navtab.fragement_home;



import java.io.Serializable;
import java.util.List;

/**
 * Created by KNOTT on 12/5/2560.
 */

public class Promotions implements PromotinDataSet,Serializable {

    private final List<Promotion> promotionList;

    public Promotions(List<Promotion> productList) {
        this.promotionList = productList;
    }

    @Override
    public int size() {
        return promotionList.size();
    }

    @Override
    public Promotion get(int position) {
        return promotionList.get(position);
    }

    @Override
    public long getId(int position) {
        return position;
    }
}
