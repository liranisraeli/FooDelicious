package com.example.foodelicious.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodelicious.Adapters.IngredientAdapter;
import com.example.foodelicious.Adapters.ContentRecipeAdapter;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ContentRecipeFragment extends Fragment {

    private AppCompatActivity activity;
    private MaterialToolbar panel_Toolbar_Top;
    private TextView recipe_content_LBL_title;
    private TextView recipe_content_LBL_category;
    private TextView editItem_EDT_notes;
    private RecyclerView ingredients_RECYC;



    private final MyDataManager dataManager = MyDataManager.getInstance();


    private ArrayList<Ingredient> myIngredients = new ArrayList();
    IngredientAdapter ingredientAdapter;

    public Fragment setActivity(AppCompatActivity activity){
        this.activity=activity;
        return this;
    }

    public ContentRecipeFragment() {
        // Required empty public constructor
    }

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_context_recipe, container, false);
        findViews(view);
        loadData();
        
        return view;
    }



    private void findViews(View view) {
//        Context_Recipe_RECYC = view.findViewById(R.id.Context_Recipe_RECYC);
        panel_Toolbar_Top = getActivity().findViewById(R.id.panel_Toolbar_Top);
        recipe_content_LBL_title = view.findViewById(R.id.recipe_content_LBL_title);
        recipe_content_LBL_category = view.findViewById(R.id.recipe_content_LBL_category);
        editItem_EDT_notes = view.findViewById(R.id.editItem_EDT_notes);
        ingredients_RECYC = view.findViewById(R.id.ingredients_RECYC);
        Log.d("namer",dataManager.getCurrentRecipe().getName());
        panel_Toolbar_Top.setTitle(dataManager.getCurrentRecipe().getName());






        //ContentRecipeAdapter recipeContentAdapter;
        // recipeContentAdapter = new ContentRecipeAdapter(this.activity,dataManager.getCurrentRecipe());


//        Context_Recipe_RECYC.setLayoutManager(new GridLayoutManager(this.activity,1));
//        Context_Recipe_RECYC.setAdapter(recipeContentAdapter);
       // recipeContentAdapter.notifyDataSetChanged();

    }




    private void loadData() {
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

        //ingredientAdapter.notifyDataSetChanged();

    }



}