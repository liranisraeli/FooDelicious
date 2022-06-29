package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.foodelicious.CallBacks.CallBackClick;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final MyDataManager dataManager = MyDataManager.getInstance();
    private Activity activity;
    private ArrayList<MyCategory> categories;
    private CallBackClick callBackCategoryClick;

    public CategoriesAdapter(Activity activity, ArrayList<MyCategory> categories, CallBackClick callBackCategoryClick){
        this.activity = activity;
        this.categories = categories;
        this.callBackCategoryClick = callBackCategoryClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_cards, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CategoryHolder holder = (CategoryHolder) viewHolder;
        MyCategory category = getItem(position);

        holder.list_LBL_title.setText(category.getTitle());
        holder.list_LBL_counter.setText("recipes amount " + category.getItems_Counter());

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
        private MaterialTextView list_LBL_counter;


        public CategoryHolder(final View itemView) {
            super(itemView);
            list_IMG_image = itemView.findViewById(R.id.list_IMG_image);
            list_LBL_title = itemView.findViewById(R.id.list_LBL_title);
            list_LBL_counter = itemView.findViewById(R.id.list_LBL_counter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBackCategoryClick != null) {
                        ArrayList<MyRecipe> recipes=new ArrayList<MyRecipe>();
                        for(MyRecipe recipe: dataManager.getMyRecipes()){
                            if(recipe.getCategory().equals(dataManager.getCategoriesName().get(getAdapterPosition()))){
                                recipes.add(recipe);
                            }
                        }
                        dataManager.setFilteredCurrentRecipes(recipes);
                        dataManager.setCurrentCategoryName(dataManager.getCategoriesName().get(getAdapterPosition()));
                        callBackCategoryClick.onClicked();
                    }
                }
            });

        }

    }
}

