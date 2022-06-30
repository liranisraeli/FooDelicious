package com.example.foodelicious.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodelicious.Adapters.RecipeAdapter;
import com.example.foodelicious.CallBacks.CallBackClick;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.google.android.material.appbar.MaterialToolbar;


public class RecipesFragment extends Fragment {

    private AppCompatActivity activity;
    private RecyclerView recipes_RECYC;
    private MaterialToolbar panel_Toolbar_Top;

    private final MyDataManager dataManager = MyDataManager.getInstance();

    CallBackClick callBackRecipeClick = new CallBackClick(){
        @Override
        public void onClicked() {
            getParentFragmentManager().beginTransaction().replace(R.id.panel_Fragment, ContentRecipeFragment.class,null).commit();
        }

        @Override
        public void favoriteClicked(int pos, MyRecipe recipe) {
            dataManager.getMyRecipes().get(pos).setFavorite(!recipe.isFavorite());
            dataManager.addNewRecipe(dataManager.getMyRecipes().get(pos));
            recipes_RECYC.getAdapter().notifyItemChanged(pos);
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
        dataManager.setMyRecipesPath("recipe");
        findViews(view);


        return view;
    }

    private void findViews(View view) {
        RecipeAdapter recipeAdapter;
        panel_Toolbar_Top = getActivity().findViewById(R.id.panel_Toolbar_Top);
        recipes_RECYC = view.findViewById(R.id.recipes_RECYC);
        if(dataManager.getPath().equals("categories")){
            panel_Toolbar_Top.setTitle(dataManager.getCurrentCategoryName());
            recipeAdapter = new RecipeAdapter(this.activity,dataManager.getFilteredCurrentRecipes(),callBackRecipeClick);
        }else{
            panel_Toolbar_Top.setTitle("All Recipes");
            recipeAdapter = new RecipeAdapter(this.activity,dataManager.getMyRecipes(),callBackRecipeClick);
        }

        recipes_RECYC.setLayoutManager(new GridLayoutManager(this.activity,1));
        recipes_RECYC.setAdapter(recipeAdapter);
        //recipeAdapter.notifyDataSetChanged();


    }
}