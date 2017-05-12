package com.knott.navtab.fragement_home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.knott.navtab.R;
import com.knott.navtab.listproduce.Product;
import com.knott.navtab.listproduce.ProductClickListener;
import com.knott.navtab.listproduce.ProductDataSet;

public class PromotionAdapter extends BaseAdapter {

    private final PromotinDataSet promotinDataSet;
    private final LayoutInflater layoutInflater;

    public PromotionAdapter(PromotinDataSet promotinDataSet1,LayoutInflater layoutInflater) {
        this.promotinDataSet = promotinDataSet1;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return promotinDataSet.size();
    }

    @Override
    public Promotion getItem(int position) {
        return promotinDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return promotinDataSet.getId(position);
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

        Promotion promotion = promotinDataSet.get(position);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        update(viewHolder, promotion);
        return view;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.layout_home, parent, false);
    }

    private void update(ViewHolder viewHolder, final Promotion promotion) {
        viewHolder.name.setText(promotion.content);

    }


    public static final class ViewHolder {
        final TextView name;


//        final TextView price;
//        final ImageView list_image;

        public static ViewHolder from(View view) {
            return new ViewHolder(
                    ((TextView) view.findViewById(R.id.textView9))
//                   , ((TextView) view.findViewById(R.id.plist_price_text))
            );
        }

        public ViewHolder(TextView name) {
            this.name = name;
//            this.price = price;
//            this.list_image = list_image;
        }
    }

}
