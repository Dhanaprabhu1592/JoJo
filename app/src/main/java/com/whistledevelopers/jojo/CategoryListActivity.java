package com.whistledevelopers.jojo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    List<CategoryList> categoryList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        categoryList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.cateory_recycler);

        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));
        categoryList.add(new CategoryList("Whistle Developers","Chennai","9876543210","5"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        CategoryListAdapter categoryListAdapter=new CategoryListAdapter(this,categoryList);
        recyclerView.setAdapter(categoryListAdapter);




    }
}
