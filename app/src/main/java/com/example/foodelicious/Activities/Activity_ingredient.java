package com.example.foodelicious.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodelicious.Adapters.IngredientAdapter;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;

public class Activity_ingredient extends AppCompatActivity {
    private RecyclerView ingredient_LST_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ingredient_LST_items = findViewById(R.id.ingredient_LST_items);

//        ArrayList<Ingredient> liqueurs = DataManager.generateliqueurs();
//        IngredientAdapter ingredientAdapter = new IngredientAdapter(this, liqueurs);
        ingredient_LST_items.setLayoutManager(new LinearLayoutManager(this));
        ingredient_LST_items.setHasFixedSize(true);
//        ingredient_LST_items.setAdapter(ingredientAdapter);

//        ingredientAdapter.setLiqueurlistener(new IngredientAdapter.Ingredientlistener() {
//            @Override
//            public void clicked(Ingredient liqueur, int position) {
//                Toast.makeText(Activity_ingredient.this, liqueur.getName(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void plus(Ingredient liqueur, int position) {
//                liqueur.addAmount(1);
//                ingredient_LST_items.getAdapter().notifyItemChanged(position);
//            }
//
//            @Override
//            public void minus(Ingredient liqueur, int position) {
//                liqueur.addAmount(-1);
//                ingredient_LST_items.getAdapter().notifyItemChanged(position);
//            }
//        });
    }
}