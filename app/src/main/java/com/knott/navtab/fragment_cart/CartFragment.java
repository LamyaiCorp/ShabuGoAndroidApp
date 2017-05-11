package com.knott.navtab.fragment_cart;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.knott.navtab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_cart, container, false);
        int[] img = {R.drawable.ic_menu_camera,R.drawable.ic_menu_gallery,R.drawable.ic_menu_manage
        };
        String[] list = { "Aerith Gainsborough", "Barret Wallace", "Cait Sith"};

        ListView cartList = (ListView) rootview.findViewById(R.id.list_cart_item);
        CartAdapter cartAdapter = new CartAdapter(getActivity(),list,img);
        cartList.setAdapter(cartAdapter);

        return rootview;
    }

    private class CartAdapter extends BaseAdapter{

        Context mContext;
        String[] strName;
        int[] resId;

        public CartAdapter(FragmentActivity activity, String[] list, int[] img) {

            this.mContext = activity;
            this.strName = list;
            this.resId = img;
        }

        @Override
        public int getCount() {
            return strName.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootview = null;
            TextView textView = null;
            ImageView imageView = null;

            LayoutInflater mInflater ;
            if(convertView == null) {
                mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rootview = mInflater.inflate(R.layout.fragment_cart, parent, false);

                textView = (TextView) rootview.findViewById(R.id.text_cart_name);

                imageView = (ImageView) rootview.findViewById(R.id.list_cart_image);


            }

           if(resId != null) {
               imageView.setBackgroundResource(resId[position]);
           }
            textView.setText(strName[position]);

            return rootview;
        }
    }
}
