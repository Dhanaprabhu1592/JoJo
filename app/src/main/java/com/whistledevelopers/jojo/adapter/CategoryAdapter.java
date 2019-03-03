package com.whistledevelopers.jojo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.activity.SubCategoryActivity;
import com.whistledevelopers.jojo.model.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {
    Context context;
    List<Categories> categoryList;
    public ArrayList<Categories> orig;

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

        final Categories categories=categoryList.get(position);
        holder.txtCategory.setText(categories.getName());
        //if(categories.getCover()!=null){
            System.out.println("Cover"+String.valueOf(categories.getParentId()));
            //}
/*
        Glide.with(context)
                .load("https://images.vector-images.com/clp5/267529/clp3133571.jpg")
                .apply(new RequestOptions().placeholder(R.drawable.ic_file_download_black_24dp).error(R.drawable.error_24dp))
                .into(holder.imageCategory);
*/
        Picasso.with(context)
                //.load("https://images.vector-images.com/clp5/267529/clp3133571.jpg")
                .load(categories.getCover())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.imageCategory);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSubCategory=new Intent(context,SubCategoryActivity.class);
                intentToSubCategory.putExtra("category",String.valueOf(categories.getId()));
                context.startActivity(intentToSubCategory);

                //context.startActivity(new Intent(context,SubCategoryActivity.class));

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
        ImageView imageCategory;
        LinearLayout linearLayout;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            txtCategory=(TextView) itemView.findViewById(R.id.txt_category);
            imageCategory=(ImageView) itemView.findViewById(R.id.image_category);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear_cateogory);

        }
    }
}
