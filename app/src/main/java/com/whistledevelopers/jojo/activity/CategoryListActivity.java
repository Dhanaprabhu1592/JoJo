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
import com.whistledevelopers.jojo.adapter.CategoryAdapter;
import com.whistledevelopers.jojo.model.Categories;
import com.whistledevelopers.jojo.model.CategoryList;
import com.whistledevelopers.jojo.adapter.CategoryListAdapter;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.model.Experts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryListActivity extends AppCompatActivity {
    List<Experts> expertsList;
    RecyclerView recyclerView;
    TextView textView_error;
    private ProgressBar spinner;
    String experts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        expertsList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.cateory_recycler);
        textView_error=(TextView)findViewById(R.id.textview_error);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        Intent intent = getIntent();
        experts=intent.getStringExtra("experts");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        final Call<Experts> expertsCall = api.getExperts(experts);
        expertsCall.enqueue(new Callback<Experts>() {
            @Override
            public void onResponse(Call<Experts> call, Response<Experts> response) {
                spinner.setVisibility(View.GONE);
                List<Experts> experts = response.body().getData();
                //check data is have atleast onr object
                if(experts.size()==0){
                textView_error.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < experts.size(); i++) {
                    expertsList.add(new Experts(
                            experts.get(i).getName(),
                            experts.get(i).getAddress(),
                            experts.get(i).getMobile(),
                            experts.get(i).getExperience(),
                            experts.get(i).getId(),
                            experts.get(i).getAvailability(),
                            experts.get(i).getProfile()
                    ));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(CategoryListActivity.this));
                recyclerView.setHasFixedSize(true);
                CategoryListAdapter categoryListAdapter = new CategoryListAdapter(CategoryListActivity.this, expertsList);
                recyclerView.setAdapter(categoryListAdapter);

            }

            @Override
            public void onFailure(Call<Experts> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(CategoryListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}
