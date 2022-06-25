package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface CategoryListener {
        void clicked(MyCategory category, int position);
    }

    private Activity activity;
    private ArrayList<MyCategory> categories = new ArrayList<>();
    private CategoryListener categorylistener;

    public CategoriesAdapter(Activity activity, ArrayList<MyCategory> categories, CategoryListener categorylistener){
        this.activity = activity;
        this.categories = categories;
        this.categorylistener = categorylistener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_cards, parent, false);
        CategoryHolder categoryHolder = new CategoryHolder(view);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final CategoryHolder holder = (CategoryHolder) viewHolder;
        MyCategory category = getItem(position);

        holder.list_LBL_title.setText(category.getTitle());
//        if(list.getItems_Counter() == 0)
//            holder.list_LBL_amount.setText("There are no items yet");
//        else
//            holder.list_LBL_amount.setText("" + list.getItems_Counter());

        Glide
                .with(activity)
                .load(category.getImage_cover())
                .into(holder.list_IMG_image);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public MyCategory getItem(int position) {
        return categories.get(position);
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView list_IMG_image;
        private MaterialTextView list_LBL_title;


        public CategoryHolder(View itemView) {
            super(itemView);
            list_IMG_image = itemView.findViewById(R.id.list_IMG_image);
            list_LBL_title = itemView.findViewById(R.id.list_LBL_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (categorylistener != null) {
                        categorylistener.clicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }

    }
}

