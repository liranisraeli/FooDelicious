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

    public interface IngredientListener {
        void clicked(Ingredient ingredient, int position);
        void plus(Ingredient ingredient, int position);
        void minus(Ingredient ingredient, int position);
        void longClick(Ingredient ingredient, int position);
    }

    private Activity activity;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private IngredientListener ingredientlistener;

    public IngredientAdapter(Activity activity, ArrayList<Ingredient> ingredients){
        this.activity = activity;
        this.ingredients = ingredients;
    }

    public void setIngredientlistener(IngredientListener ingredientlistener) {
        this.ingredientlistener = ingredientlistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredient, parent, false);
        IngredientHolder ingredientHolder = new IngredientHolder(view);
        return ingredientHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final IngredientHolder holder = (IngredientHolder) viewHolder;
        Ingredient ingredient = getItem(position);

        holder.ingridient_LBL_title.setText(ingredient.getName());
        holder.ingridient_LBL_amount.setText("" + ingredient.getAmount());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }



    class IngredientHolder extends RecyclerView.ViewHolder {

        private MaterialTextView ingridient_LBL_title;
        private MaterialTextView ingridient_LBL_amount;
        private MaterialButton ingridient_BTN_plus;
        private MaterialButton ingridient_BTN_minus;


        public IngredientHolder(View itemView) {
            super(itemView);
            ingridient_LBL_title = itemView.findViewById(R.id.ingridient_LBL_title);
            ingridient_LBL_amount = itemView.findViewById(R.id.ingridient_LBL_amount);
            ingridient_BTN_plus = itemView.findViewById(R.id.ingridient_BTN_plus);
            ingridient_BTN_minus = itemView.findViewById(R.id.ingridient_BTN_minus);

            ingridient_BTN_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ingredientlistener != null) {
                        ingredientlistener.minus(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            ingridient_BTN_plus.setOnClickListener(new View.OnClickListener() {
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

                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ingredientlistener.longClick(getItem(getAdapterPosition()), getAdapterPosition());
                            return true;
                        }
                    });
        }

    }
}