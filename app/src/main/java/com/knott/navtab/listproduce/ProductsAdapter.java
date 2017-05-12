package com.knott.navtab.listproduce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.knott.navtab.R;

import java.io.InputStream;

public class ProductsAdapter extends BaseAdapter {

    private final ProductDataSet productDataSet;
    private final ProductClickListener productClickListener;
    private final LayoutInflater layoutInflater;

    public ProductsAdapter(ProductDataSet productDataSet, ProductClickListener productClickListener, LayoutInflater layoutInflater) {
        this.productDataSet = productDataSet;
        this.productClickListener = productClickListener;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return productDataSet.size();
    }

    @Override
    public Product getItem(int position) {
        return productDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productDataSet.getId(position);
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

        Product product = productDataSet.get(position);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        update(viewHolder, product);
        return view;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.layout_list_menu, parent, false);
    }

    private void update(ViewHolder viewHolder, final Product product) {
        viewHolder.name.setText(product.name);
        viewHolder.price.setText(String.valueOf(product.price));
        viewHolder.quantity_item.setText(String.valueOf(product.quantity));
        new DownloadImageTask(viewHolder.list_image).execute(product.img);
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onMinusClick(product);
            }
        });
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onPlusClick(product);
            }
        });
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            pd.show();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
//            pd.dismiss();
            bmImage.setImageBitmap(result);
        }
    }

    public static final class ViewHolder {
        final TextView name;
        final TextView price;
        final View minus;
        final View plus;
        final ImageView list_image;
        final EditText quantity_item;

        public static ViewHolder from(View view) {
            return new ViewHolder(
                    ((TextView) view.findViewById(R.id.text_cart_name)),
                    ((TextView) view.findViewById(R.id.plist_price_text)),
                    view.findViewById(R.id.imageButton_minus),
                    view.findViewById(R.id.imageButton_plus),
                    ((ImageView)view.findViewById(R.id.list_image)),
                    (EditText)view.findViewById(R.id.quantity_item));
        }

        public ViewHolder(TextView name, TextView price, View minus, View plus, ImageView list_image, EditText quantity_item) {
            this.name = name;
            this.price = price;
            this.minus = minus;
            this.plus = plus;
            this.list_image = list_image;
            this.quantity_item = quantity_item;
        }
    }

    

}
