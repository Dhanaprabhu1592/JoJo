package com.whistledevelopers.jojo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.model.Categories;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.adapter.SubCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Categories> categoryList;
    String category;
    private ProgressBar spinner;
    TextView textView_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        textView_error=(TextView)findViewById(R.id.textview_error);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        category=intent.getStringExtra("category");

        categoryList = new ArrayList<>();

        /*categoryList.add(new Categories("Tv Electrician"));
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
*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Call<Categories> call = api.getSubCategory(category);
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                spinner.setVisibility(View.GONE);
                List<Categories> categories = response.body().getData();
                if(categories.size()==0){
                textView_error.setVisibility(View.VISIBLE);
                }

                for(int i=0;i<categories.size();i++){
                    categoryList.add(new Categories(categories.get(i).getName(),categories.get(i).getCover(),categories.get(i).getId()));
                }
                SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(SubCategoryActivity.this, categoryList);
                recyclerView.setAdapter(categoryAdapter);


            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(SubCategoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
