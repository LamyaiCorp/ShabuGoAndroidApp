package com.knott.navtab.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.knott.navtab.R;
import com.knott.navtab.fragment_munu.DrinkFragment;
import com.knott.navtab.fragment_munu.FreshFoodFragment;
import com.knott.navtab.fragment_munu.VegetableFragment;
import com.knott.navtab.fragment_order.TotalOrderFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabMenuFragment extends Fragment {

    View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static int int_items = 3 ;


    public TabMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tab_main, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        Button oderBooking = (Button) rootView.findViewById(R.id.button_oderBoooking);
        oderBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager myFragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                myFragmentTransaction.replace(R.id.content_view, new TotalOrderFragment()).commit();

            }
        });


        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new FreshFoodFragment();
                case 1 : return new VegetableFragment();
                case 2 : return new DrinkFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Fresh Food";
                case 1 :
                    return "Vegetable";
                case 2 :
                    return "Drinking";
            }
            return null;
        }
    }




}
