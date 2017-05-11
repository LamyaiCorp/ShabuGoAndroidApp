package com.knott.navtab.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.knott.navtab.R;


public class NFCFragment extends Fragment {


    public NFCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootViw = inflater.inflate(R.layout.fragment_nfc, container, false);

        Button nfc_clivk = (Button) rootViw.findViewById(R.id.button_nfc);
        nfc_clivk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager myFragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                myFragmentTransaction.replace(R.id.content_view, new TabMenuFragment()).commit();
            }
        });
        return rootViw;
    }


}