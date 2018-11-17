package com.whistledevelopers.jojo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListHolder> {
    Context context;
    List<CategoryList> categoryList;

    public CategoryListAdapter(CategoryListActivity context, List<CategoryList> categoryList) {
        this.context=context;
        this.categoryList=categoryList;
    }

    @NonNull
    @Override
    public CategoryListAdapter.CategoryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.categories_list_row,parent,false);
        return new CategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.CategoryListHolder holder, int position) {

        CategoryList categoryLists=categoryList.get(position);
        holder.text_name.setText(categoryLists.getName());
        holder.text_address.setText(categoryLists.getArea());
        holder.text_number.setText(categoryLists.getNumber());
        holder.text_exp.setText("Experience : "+categoryLists.getExperience()+"  Years");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,CategoryDetailActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryListHolder extends RecyclerView.ViewHolder {
        TextView text_name,text_address,text_number,text_exp;
        RelativeLayout relativeLayout;
        public CategoryListHolder(View itemView) {
            super(itemView);
            text_name=(TextView)itemView.findViewById(R.id.txt_category_name);
            text_address=(TextView)itemView.findViewById(R.id.txt_category_address);
            text_number=(TextView)itemView.findViewById(R.id.txt_category_number);
            text_exp=(TextView)itemView.findViewById(R.id.txt_category_experience);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relative_list);
        }
    }
}
