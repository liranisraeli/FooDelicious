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

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    private AppCompatActivity activity;
    private RecyclerView favorite_recipes_RECYC;
    private MaterialToolbar panel_Toolbar_Top;

    private final MyDataManager dataManager = MyDataManager.getInstance();

    CallBackClick callBackFavoriteRecipeClick = new CallBackClick(){
        @Override
        public void onClicked() {

            getParentFragmentManager().beginTransaction().replace(R.id.panel_Fragment, ContentRecipeFragment.class,null).commit();
        }

        @Override
        public void favoriteClicked(int pos, MyRecipe recipe) {
            //dataManager.getMyRecipes().get(pos).setFavorite(!recipe.isFavorite());
            dataManager.getMyRecipes().get(pos).setFavorite(!recipe.isFavorite());
            dataManager.addNewRecipe(dataManager.getMyRecipes().get(pos));
            favorite_recipes_RECYC.getAdapter().notifyItemChanged(pos);
        }
    };


    public Fragment setActivity(AppCompatActivity activity){
        this.activity=activity;
        return this;
    }


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        findViews(view);



        return view;
    }

    private void findViews(View view) {
        panel_Toolbar_Top = getActivity().findViewById(R.id.panel_Toolbar_Top);
        favorite_recipes_RECYC = view.findViewById(R.id.favorite_recipes_RECYC);
        panel_Toolbar_Top.setTitle("Favorites");
        ArrayList<MyRecipe> myFavoritesRecipes=new ArrayList<>();
        filterFavorites(myFavoritesRecipes);
        RecipeAdapter recipeAdapter = new RecipeAdapter(this.activity,myFavoritesRecipes,callBackFavoriteRecipeClick);


        favorite_recipes_RECYC.setLayoutManager(new GridLayoutManager(this.activity,1));
        favorite_recipes_RECYC.setAdapter(recipeAdapter);
        recipeAdapter.notifyDataSetChanged();


    }

    private void filterFavorites(ArrayList<MyRecipe> myFavoritesRecipes) {
        for (MyRecipe recipe: dataManager.getMyRecipes()){
            if(recipe.isFavorite()){
                myFavoritesRecipes.add(recipe);
            }
        }
    }
}