package com.whistledevelopers.jojo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    public static List<Categories> categoryList;
    CategoryAdapter categoryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_options);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        categoryList = new ArrayList<>();


        categoryList.add(new Categories("Electrician"));
        categoryList.add(new Categories("Plumber"));
        categoryList.add(new Categories("Carpenter"));
        categoryList.add(new Categories("Two Wheeler Mechanic"));
        categoryList.add(new Categories("Car Mechanic"));
        categoryList.add(new Categories("Painters"));
        categoryList.add(new Categories("Sports and Coaching"));
        categoryList.add(new Categories("Tours and Travel Agents"));
        categoryList.add(new Categories("Cook"));
        categoryList.add(new Categories("Wedding and Event Management"));
        categoryList.add(new Categories("Pet Aanimal Trainers"));
        categoryList.add(new Categories("Contractors"));
        categoryList.add(new Categories("News Paper Delivery Boy"));
        categoryList.add(new Categories("Laundry"));
        categoryList.add(new Categories("Logistics"));
        categoryList.add(new Categories("Retail"));
        categoryList.add(new Categories("Mobile Repair"));
        categoryList.add(new Categories("Help Others"));
        categoryList.add(new Categories("Constructions"));
        categoryList.add(new Categories("Groceries"));
        categoryList.add(new Categories("Vehicle Rental and Drivers"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CategoryAdapter categoryAdapter = (CategoryAdapter) recyclerView.getAdapter();
                Filter filter = categoryAdapter.getFilter();
                filter.filter(newText);
                return true;

            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }
}
