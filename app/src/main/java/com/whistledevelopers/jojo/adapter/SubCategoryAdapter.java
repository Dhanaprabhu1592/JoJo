package com.whistledevelopers.jojo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.activity.CategoryListActivity;
import com.whistledevelopers.jojo.activity.SubCategoryActivity;
import com.whistledevelopers.jojo.model.Categories;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.CategoryViewHolder> implements Filterable {
    Context context;
    List<Categories> categoryList;
    public ArrayList<Categories> orig;

    public SubCategoryAdapter(Context context, List<Categories> categoryList) {
        this.context=context;
        this.categoryList=categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sub_categories_row,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        final Categories categories=categoryList.get(position);
        holder.txtCategory.setText(categories.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSubCategory=new Intent(context,CategoryListActivity.class);
                intentToSubCategory.putExtra("experts",String.valueOf(categories.getId()));
                context.startActivity(intentToSubCategory);


            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Categories> results = new ArrayList<Categories>();
                if (orig == null)
                    orig = (ArrayList<Categories>) categoryList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Categories g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                categoryList = (ArrayList<Categories>) results.values;
                notifyDataSetChanged();

            }
        };

    }



    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        LinearLayout linearLayout;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            txtCategory=(TextView) itemView.findViewById(R.id.txt_category);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear_cateogory);

        }
    }
}
