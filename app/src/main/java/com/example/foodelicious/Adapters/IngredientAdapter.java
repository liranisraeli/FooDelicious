package com.example.foodelicious.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodelicious.Activities.Activity_create_recipe;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface IngredientListener  {
        void clicked(Ingredient ingredient, int position,String name);
        void plus(Ingredient ingredient, int position);
        void minus(Ingredient ingredient, int position);
    }

    private final MyDataManager dataManager = MyDataManager.getInstance();


    private Activity activity;
    private ArrayList<Ingredient> ingredients;
    private IngredientListener ingredientlistener;
    Ingredient ingredient;
    private String text;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        IngredientHolder holder = (IngredientHolder) viewHolder;
        ingredient = getItem(position);

        holder.ingredient_LBL_amount.setText("" + ingredient.getAmount());
        holder.ingredient_LBL_Name.getEditText().setText(ingredient.getName());

        holder.ingredient_LBL_Name.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    ingredientlistener.clicked(ingredient, holder.getAdapterPosition(), text);
                    closeKeyboard();
                    return true;
                }
                return false;
            }
        });

        holder.ingredient_LBL_Name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text= charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        }) ;


    }

    private void closeKeyboard() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }



    private class IngredientHolder extends RecyclerView.ViewHolder {

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

            if(dataManager.getMyRecipesPath().equals("recipe")){
                ingredient_LBL_Name.setHint("Ingredient Name:");
                ingredient_BTN_plus.setVisibility(View.INVISIBLE);
                ingredient_BTN_minus.setVisibility(View.INVISIBLE);

            }else if(dataManager.getMyRecipesPath().equals("create")){
                ingredient_LBL_Name.setHint("Enter Ingredient");
                ingredient_BTN_plus.setVisibility(View.VISIBLE);
                ingredient_BTN_minus.setVisibility(View.VISIBLE);
            }


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

        }

    }
}