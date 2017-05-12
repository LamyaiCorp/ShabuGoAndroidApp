package com.knott.navtab.fragment_cart;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.knott.navtab.R;


public class CartItemAdapter extends BaseAdapter {

    private final CartitemSet cartitemSet;
    private final LayoutInflater layoutInflater;

    public CartItemAdapter(CartitemSet cartitemSet,LayoutInflater layoutInflater) {
        this.cartitemSet = cartitemSet;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return cartitemSet.size();
    }

    @Override
    public CartItem getItem(int position) {
        return cartitemSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartitemSet.getId(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = createView(parent);
            view.setTag(ViewHolder.from(view));
        }

        CartItem cartItem = cartitemSet.get(position);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        update(viewHolder, cartItem);
        return view;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.layout_cart_item, parent, false);
    }

    private void update(ViewHolder viewHolder, final CartItem cartItem) {

        viewHolder.name.setText(cartItem.name);
        viewHolder.quantity.setText(String.valueOf(cartItem.getTotalQuntityItem()));

    }

    public static final class ViewHolder {
        final TextView name;
        final TextView quantity;

        public static ViewHolder from(View view) {
            return new ViewHolder(
                    ((TextView) view.findViewById(R.id.text_cart_name)),
                    ((TextView) view.findViewById(R.id.text_cart_quantity_item))

                    );
        }

        public ViewHolder(TextView name, TextView quantity) {
            this.name = name;
            this.quantity = quantity;
        }
    }

}
