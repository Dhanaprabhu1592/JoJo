package com.whistledevelopers.jojo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Categories> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        categoryList = new ArrayList<>();

        categoryList.add(new Categories("Tv Electrician"));
        categoryList.add(new Categories("Tv Repair"));
        categoryList.add(new Categories("Carpenter"));
        categoryList.add(new Categories("Two Wheeler Mechanic"));
        categoryList.add(new Categories("Car Mechanic"));
        categoryList.add(new Categories("Painters"));
        categoryList.add(new Categories("Tv Electrician"));
        categoryList.add(new Categories("Tv Repair"));
        categoryList.add(new Categories("Carpenter"));
        categoryList.add(new Categories("Two Wheeler Mechanic"));
        categoryList.add(new Categories("Car Mechanic"));
        categoryList.add(new Categories("Painters"));
        categoryList.add(new Categories("Tv Electrician"));
        categoryList.add(new Categories("Tv Repair"));
        categoryList.add(new Categories("Carpenter"));
        categoryList.add(new Categories("Two Wheeler Mechanic"));
        categoryList.add(new Categories("Car Mechanic"));
        categoryList.add(new Categories("Painters"));

        SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(this, categoryList);
        recyclerView.setAdapter(categoryAdapter);

    }
}
