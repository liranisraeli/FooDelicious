package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Ingredientlistener {
        void clicked(Ingredient ingredient, int position);
        void plus(Ingredient ingredient, int position);
        void minus(Ingredient ingredient, int position);
    }

    private Activity activity;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private Ingredientlistener liqueurlistener;

    public IngredientAdapter(Activity activity, ArrayList<Ingredient> liqueurs){
        this.activity = activity;
        this.ingredients = ingredients;
    }

    public void setLiqueurlistener(Ingredientlistener ingredientlistener) {
        this.liqueurlistener = ingredientlistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredient, parent, false);
        LiqueurHolder liqueurHolder = new LiqueurHolder(view);
        return liqueurHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final LiqueurHolder holder = (LiqueurHolder) viewHolder;
        Ingredient ingredient = getItem(position);

        holder.liqueur_LBL_title.setText(ingredient.getName());
        holder.liqueur_LBL_amount.setText("" + ingredient.getAmount());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }



    class LiqueurHolder extends RecyclerView.ViewHolder {

        private MaterialTextView liqueur_LBL_title;
        private MaterialTextView liqueur_LBL_amount;
        private MaterialButton liqueur_BTN_plus;
        private MaterialButton liqueur_BTN_minus;


        public LiqueurHolder(View itemView) {
            super(itemView);
            liqueur_LBL_title = itemView.findViewById(R.id.liqueur_LBL_title);
            liqueur_LBL_amount = itemView.findViewById(R.id.liqueur_LBL_amount);
            liqueur_BTN_plus = itemView.findViewById(R.id.liqueur_BTN_plus);
            liqueur_BTN_minus = itemView.findViewById(R.id.liqueur_BTN_minus);

            liqueur_BTN_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (liqueurlistener != null) {
                        liqueurlistener.minus(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            liqueur_BTN_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (liqueurlistener != null) {
                        liqueurlistener.plus(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (liqueurlistener != null) {
                        liqueurlistener.clicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }

    }
}