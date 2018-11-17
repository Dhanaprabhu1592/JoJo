package com.whistledevelopers.jojo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Categories> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_options);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        categoryList=new ArrayList<>();


        categoryList.add( new Categories("Electrician"));
        categoryList.add( new Categories("Plumber"));
        categoryList.add( new Categories("Carpenter"));
        categoryList.add( new Categories("Two Wheeler Mechanic"));
        categoryList.add( new Categories("Car Mechanic"));
        categoryList.add( new Categories("Painters"));

        CategoryAdapter categoryAdapter=new CategoryAdapter(getActivity(),categoryList);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

}
