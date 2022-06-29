package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodelicious.CallBacks.CallBackClick;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final MyDataManager dataManager = MyDataManager.getInstance();

    private Activity activity;
    private ArrayList<MyRecipe> recipes;
    private CallBackClick callBackRecipeClick;



    public RecipeAdapter(Activity activity, ArrayList<MyRecipe> recipes,  CallBackClick callBackRecipeClick){
        this.activity = activity;
        this.recipes = recipes;
        this.callBackRecipeClick = callBackRecipeClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_cards, parent, false);
        RecipeAdapter.RecipeHolder recipeHolder = new RecipeAdapter.RecipeHolder(view);
        return recipeHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final RecipeAdapter.RecipeHolder holder = (RecipeAdapter.RecipeHolder) viewHolder;
        MyRecipe recipe = getRecipe(position);

        holder.recipe_LBL_title.setText(recipe.getName());
        if(recipe.isFavorite()){
            holder.item_IMG_favorite.setImageResource(R.drawable.ic_heart_filled);
        }else{
            holder.item_IMG_favorite.setImageResource(R.drawable.ic_heart_empty);
        }
    }

    @Override
    public int getItemCount() {
        if(recipes==null){
            return 0;
        }
        return recipes.size();
    }

    public MyRecipe getRecipe(int position) {
        return recipes.get(position);
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView recipe_IMG_image;
        private MaterialTextView recipe_LBL_title;
        private AppCompatImageView item_IMG_favorite;



        public RecipeHolder(View itemView) {
            super(itemView);
            recipe_IMG_image = itemView.findViewById(R.id.list_IMG_image);
            recipe_LBL_title = itemView.findViewById(R.id.recipe_LBL_title);
            item_IMG_favorite = itemView.findViewById(R.id.item_IMG_favorite);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataManager.setCurrentRecipe(getRecipe(getAdapterPosition()));
                    callBackRecipeClick.onClicked();
                }
            });

            item_IMG_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackRecipeClick.favoriteClicked(getAdapterPosition(),getRecipe(getAdapterPosition()));
                }
            });
        }

    }
}
