package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;

public class ContentRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final MyDataManager dataManager = MyDataManager.getInstance();

    private Activity activity;
    private MyRecipe recipe;


    public ContentRecipeAdapter(Activity activity, MyRecipe recipe) {
        this.activity = activity;
        this.recipe = recipe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_context_recipe, parent, false);
        ContentRecipeHolder contentRecipeHolder = new ContentRecipeHolder(view);
        return contentRecipeHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final ContentRecipeHolder holder = (ContentRecipeHolder) viewHolder;
        recipe = dataManager.getCurrentRecipe();


    }

    @Override
    public int getItemCount() {
        if (recipe == null) {
            return 0;
        }
        return 0;
    }
//
//    public MyRecipe getRecipe(int position) {
//        return recipe.get(position);
//    }

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

