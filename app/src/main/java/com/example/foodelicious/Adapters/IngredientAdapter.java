package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface IngredientListener {
        void clicked(Ingredient ingredient, int position);
        void plus(Ingredient ingredient, int position);
        void minus(Ingredient ingredient, int position);
    }

    private Activity activity;
    private ArrayList<Ingredient> ingredients;
    private IngredientListener ingredientlistener;

    public IngredientAdapter(Activity activity, ArrayList<Ingredient> ingredients){
        this.activity = activity;
        this.ingredients = ingredients;
    }

    public void setIngredientListener(IngredientListener ingredientlistener) {
        this.ingredientlistener = ingredientlistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_cards, parent, false);



        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final IngredientHolder holder = (IngredientHolder) viewHolder;
        Ingredient ingredient = getItem(position);

        holder.ingredient_LBL_Name.getEditText().setText(ingredient.getName());
        holder.ingredient_LBL_amount.setText("" + ingredient.getAmount());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }



    class IngredientHolder extends RecyclerView.ViewHolder {

        private TextInputLayout ingredient_LBL_Name;
        private MaterialTextView ingredient_LBL_amount;
        private MaterialButton ingredient_BTN_plus;
        private MaterialButton ingredient_BTN_minus;


        public IngredientHolder(View itemView) {
            super(itemView);
            ingredient_LBL_Name = itemView.findViewById(R.id.ingredient_LBL_Name);
            ingredient_LBL_amount = itemView.findViewById(R.id.ingredient_LBL_amount);
            ingredient_BTN_plus = itemView.findViewById(R.id.ingredient_BTN_plus);
            ingredient_BTN_minus = itemView.findViewById(R.id.ingredient_BTN_minus);



            ingredient_BTN_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ingredientlistener != null) {
                        ingredientlistener.minus(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            ingredient_BTN_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ingredientlistener != null) {
                        ingredientlistener.plus(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ingredientlistener != null) {
                        ingredientlistener.clicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });


        }

    }
}