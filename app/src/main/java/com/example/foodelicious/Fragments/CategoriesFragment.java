package com.example.foodelicious.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodelicious.Adapters.CategoriesAdapter;
import com.example.foodelicious.CallBacks.CallBackClick;
import com.example.foodelicious.CallBacks.CallBackCreateRecipe;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;

public class CategoriesFragment extends Fragment {

    private AppCompatActivity activity;
    private CategoriesAdapter categoriesAdapter;
    private RecyclerView category_RECYC;
    private final MyDataManager dataManager = MyDataManager.getInstance();

    CallBackClick callBackCategoryClick = new CallBackClick(){
        @Override
        public void onClicked() {
//            dataManager.setPath("categories");
            getParentFragmentManager().beginTransaction().replace(R.id.panel_Fragment,RecipesFragment.class,null).commit();
        }

        @Override
        public void favoriteClicked(int pos, MyRecipe recipe) {

        }
    };

    CallBackCreateRecipe callBackCreateRecipe = new CallBackCreateRecipe(){
        @Override
        public void createRecipe() {
            categoriesAdapter.notifyDataSetChanged();
        }
    };


    public Fragment setActivity(AppCompatActivity activity){
        this.activity=activity;
        return this;
    }

    public CategoriesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        dataManager.setCallBackCreateRecipe(callBackCreateRecipe);
        findViews(view);
        initAdapter();
        return view;
    }

    private void findViews(View view) {
        category_RECYC = view.findViewById(R.id.category_RECYC);
    }

    private void initAdapter() {
        categoriesAdapter = new CategoriesAdapter(this.activity, dataManager.getMyCategories(), callBackCategoryClick);
        category_RECYC.setLayoutManager(new GridLayoutManager(this.activity,2));
        category_RECYC.setAdapter(categoriesAdapter);
        Log.d("roman 82",dataManager.getMyRecipes().size() + "");

    }

}