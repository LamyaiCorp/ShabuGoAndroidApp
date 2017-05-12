package com.knott.navtab.fragement_home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.knott.navtab.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ListView homeListview;
    Promotions pormotions;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);


        pormotions = createInitialProductList();
        homeListview = (ListView) rootview.findViewById(R.id.listview_home);
        PromotionAdapter promotionAdapter = new PromotionAdapter(pormotions,getActivity().getLayoutInflater());
        homeListview.setAdapter(promotionAdapter);
        homeListview.setDivider(null);
        return rootview;
    }


    private Promotions createInitialProductList()  {


        ArrayList<Promotion> arrayList = new ArrayList();
        for(int i = 0; i< 10; i++){
//            jsondata = obj.getJSONObject(i);
            arrayList.add(
                    new Promotion(
                            String.valueOf("aaaaaa"),
                            String.valueOf("hhhhhh")

                    )
            );
        }

        if( pormotions == null){
            pormotions = new Promotions(arrayList);

        }


        return  pormotions;
    }

}
