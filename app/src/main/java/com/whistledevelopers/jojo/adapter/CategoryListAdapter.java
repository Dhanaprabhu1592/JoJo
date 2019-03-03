package com.whistledevelopers.jojo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.activity.CategoryDetailActivity;
import com.whistledevelopers.jojo.activity.CategoryListActivity;
import com.whistledevelopers.jojo.activity.CommonActivity;
import com.whistledevelopers.jojo.model.CategoryList;
import com.whistledevelopers.jojo.model.Experts;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListHolder> {
    Context context;
    List<Experts> categoryList;

    public CategoryListAdapter(CategoryListActivity context, List<Experts> categoryList) {
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

        final Experts categoryLists=categoryList.get(position);
        holder.text_name.setText(categoryLists.getName());
        holder.text_address.setText(categoryLists.getAddress());
        holder.text_number.setText(categoryLists.getMobile());
        holder.text_exp.setText("Experience : "+categoryLists.getExperience()+"  Years");

        if(null==categoryLists.getAvailability()){
            holder.text_availability.setVisibility(View.INVISIBLE);
        }else{
            holder.text_availability.setText("Availability : "+categoryLists.getAvailability());
        }
        Picasso.with(context)
                .load(categoryLists.getProfile())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.list_image);
        holder.img_next .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSubCategory=new Intent(context,CategoryDetailActivity.class);
                intentToSubCategory.putExtra("experts",String.valueOf(categoryLists.getId()));
                context.startActivity(intentToSubCategory);

            }
        });
        holder.img_call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+categoryLists.getMobile()));
                context.startActivity(intent);
*/
              Intent intent=new Intent(context,CommonActivity.class);
              intent.putExtra("expertId",String.valueOf(categoryLists.getId()));
              intent.putExtra("expert_phone",categoryLists.getMobile());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryListHolder extends RecyclerView.ViewHolder {
        TextView text_name,text_address,text_number,text_exp,text_availability;
        RelativeLayout relativeLayout;
        ImageView img_next,img_call_button;
        CircleImageView list_image;
        public CategoryListHolder(View itemView) {
            super(itemView);
            text_name=(TextView)itemView.findViewById(R.id.txt_category_name);
            text_address=(TextView)itemView.findViewById(R.id.txt_category_address);
            text_number=(TextView)itemView.findViewById(R.id.txt_category_number);
            text_exp=(TextView)itemView.findViewById(R.id.txt_category_experience);
            text_availability=(TextView)itemView.findViewById(R.id.txt_category_availability);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relative_list);
            img_next=(ImageView)itemView.findViewById(R.id.img_next);
            img_call_button=(ImageView)itemView.findViewById(R.id.call_button);
            list_image=(CircleImageView) itemView.findViewById(R.id.list_image);
        }
    }
}
