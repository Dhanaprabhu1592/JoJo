package com.whistledevelopers.jojo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Categories> categoryList;
    public CategoryAdapter(Context context, List<Categories> categoryList) {
        this.context=context;
        this.categoryList=categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.categories_row,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Categories categories=categoryList.get(position);
        holder.txtCategory.setText(categories.getCategory());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            txtCategory=(TextView) itemView.findViewById(R.id.txt_category);

        }
    }
}
