package com.example.foodelicious.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodelicious.Adapters.RecipeAdapter;
import com.example.foodelicious.CallBacks.CallBackClick;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.R;


public class RecipesFragment extends Fragment {

    private AppCompatActivity activity;
    private RecyclerView recipes_RECYC;

    private RecipeAdapter recipeAdapter;
    private final MyDataManager dataManager = MyDataManager.getInstance();

    CallBackClick callBackRecipeClick = new CallBackClick(){
        @Override
        public void onClicked() {
            getParentFragmentManager().beginTransaction().replace(R.id.panel_Fragment,RecipesFragment.class,null).commit();
        }
    };


    public Fragment setActivity(AppCompatActivity activity){
        this.activity=activity;
        return this;
    }

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        findViews(view);


        return view;
    }

    private void findViews(View view) {
        recipes_RECYC = view.findViewById(R.id.recipes_RECYC);
        if(dataManager.getPath().equals("categories")){
            recipeAdapter = new RecipeAdapter(this.activity,dataManager.getFilteredCurrentRecipes(),callBackRecipeClick);
        }else if(dataManager.getPath().equals("categories")){
            recipeAdapter = new RecipeAdapter(this.activity,dataManager.getMyRecipes(),callBackRecipeClick);
        }

    }
}