package com.whistledevelopers.jojo.fragments;

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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.model.Categories;
import com.whistledevelopers.jojo.adapter.CategoryAdapter;
import com.whistledevelopers.jojo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    public static List<Categories> categoryList;
    CategoryAdapter categoryAdapter;
    private ProgressBar spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        spinner = (ProgressBar)view.findViewById(R.id.progressBar);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_options);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        categoryList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Call<Categories> call = api.getCategories("0");

        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                spinner.setVisibility(View.GONE);
                List<Categories> categories = response.body().getData();

                for(int i=0;i<categories.size();i++){
                    categoryList.add(new Categories(categories.get(i).getName(),categories.get(i).getCover(),categories.get(i).getId()));
                }
                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                if(!t.getMessage().equals(null)){
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Please Try again later!", Toast.LENGTH_SHORT).show();

                }
            }
        });

/*
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


*/

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.findItem(R.id.action_settings);
        menuItem.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
