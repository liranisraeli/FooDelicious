package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodelicious.Activities.Activity_create_recipe;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;

import java.util.ArrayList;

public class ContentRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final MyDataManager dataManager = MyDataManager.getInstance();

    private Activity activity;
    private ArrayList<MyRecipe> recipes;


    public ContentRecipeAdapter(Activity activity, ArrayList<MyRecipe> recipes) {
        this.activity = activity;
        this.recipes = recipes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_content_cards, parent, false);
        ContentRecipeHolder contentRecipeHolder = new ContentRecipeHolder(view);
        return contentRecipeHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final ContentRecipeHolder holder = (ContentRecipeHolder) viewHolder;
        MyRecipe recipe = getRecipe(position);


    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    public MyRecipe getRecipe(int position) {
        return recipes.get(position);
    }

    class ContentRecipeHolder extends RecyclerView.ViewHolder {
        private TextView recipe_content_LBL_title;
        private TextView recipe_content_LBL_category;
        private TextView editItem_EDT_notes;
        private RecyclerView ingredients_RECYC;

        private IngredientAdapter ingredientAdapter;


        public ContentRecipeHolder(View itemView) {
            super(itemView);
            recipe_content_LBL_title = itemView.findViewById(R.id.recipe_content_LBL_title);
            recipe_content_LBL_category = itemView.findViewById(R.id.recipe_content_LBL_category);
            editItem_EDT_notes = itemView.findViewById(R.id.editItem_EDT_notes);
            ingredients_RECYC = itemView.findViewById(R.id.ingredients_RECYC);

            recipe_content_LBL_title.setText(dataManager.getCurrentRecipe().getName());
            recipe_content_LBL_category.setText(dataManager.getCurrentRecipe().getCategory());
            editItem_EDT_notes.setText(dataManager.getCurrentRecipe().getMethodSteps());


            ingredientAdapter = new IngredientAdapter(activity, dataManager.getCurrentRecipe().getIngredients());
            ingredients_RECYC.setLayoutManager(new GridLayoutManager(activity, 1));
            ingredients_RECYC.setAdapter(ingredientAdapter);



            ingredientAdapter.setIngredientListener(new IngredientAdapter.IngredientListener() {
                @Override
                public void clicked(Ingredient ingredient, int position, String name) {
                }


                @Override
                public void plus(Ingredient ingredient, int position) {
                }

                @Override
                public void minus(Ingredient ingredient, int position) {
                }

            });

            ingredientAdapter.notifyDataSetChanged();


        }



    }

}

