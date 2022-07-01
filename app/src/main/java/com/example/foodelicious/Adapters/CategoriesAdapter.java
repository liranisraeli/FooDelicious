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
    private ArrayList<MyRecipe> recipes;

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

    public class CategoryHolder extends RecyclerView.ViewHolder {
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
                    Log.d("roman 82",dataManager.getMyRecipes().size() + "");
                    if (callBackCategoryClick != null) {
//                        dataManager.setMyRecipes(removeDuplicates(dataManager.getMyRecipes()));
                        dataManager.setCurrentCategoryName(dataManager.getCategoriesName().get(getAdapterPosition()));
                        setFilteredList(dataManager.getCurrentCategoryName());
                        dataManager.setFilteredCurrentRecipes(recipes);
                        list_LBL_counter.setText("recipes amount " + dataManager.getMyCategories().get(getAdapterPosition()).getItems_Counter());
                        callBackCategoryClick.onClicked();

                    }
                }
            });

        }

    }

    public void setFilteredList(String currentCategoryName){
        recipes = new ArrayList<>();
        for(int i=0; i<dataManager.getMyRecipes().size(); i++) {
            if(dataManager.getMyRecipes().get(i).getCategory().equals(currentCategoryName)){
                Log.d("roman print",dataManager.getMyRecipes().get(i).getCategory()+i);
                recipes.add(dataManager.getMyRecipes().get(i));
            }
        }
        notifyDataSetChanged();

    }




    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}

