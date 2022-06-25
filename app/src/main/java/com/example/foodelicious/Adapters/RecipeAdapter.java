package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface RecipeListener {
        void clicked(MyRecipe recipe, int position);
    }

    private Activity activity;
    private ArrayList<MyRecipe> recipes = new ArrayList<>();
    private RecipeAdapter.RecipeListener recipeListener;

    public RecipeAdapter(Activity activity, ArrayList<MyRecipe> recipes, RecipeAdapter.RecipeListener recipeListener){
        this.activity = activity;
        this.recipes = recipes;
        this.recipeListener = recipeListener;
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

        holder.list_LBL_title.setText(recipe.getName());
//        if(list.getItems_Counter() == 0)
//            holder.list_LBL_amount.setText("There are no items yet");
//        else
//            holder.list_LBL_amount.setText("" + list.getItems_Counter());

        Glide
                .with(activity)
                .load(R.drawable.img_chef)
                .into(holder.list_IMG_image);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public MyRecipe getRecipe(int position) {
        return recipes.get(position);
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView list_IMG_image;
        private MaterialTextView list_LBL_title;


        public RecipeHolder(View itemView) {
            super(itemView);
            list_IMG_image = itemView.findViewById(R.id.list_IMG_image);
            list_LBL_title = itemView.findViewById(R.id.list_LBL_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recipeListener != null) {
                        recipeListener.clicked(getRecipe(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }

    }
}
