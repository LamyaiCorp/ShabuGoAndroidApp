package com.knott.navtab.fragment_munu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knott.navtab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VegetableFragment extends Fragment {


    public VegetableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vegetable, container, false);

        return rootView;
    }

}
