package com.example.foodelicious.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodelicious.Adapters.IngredientAdapter;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    private AppCompatActivity activity;
    private RecyclerView ingredients_RECYC;
    private ArrayList<Ingredient> myIngredients = new ArrayList();
    IngredientAdapter ingredientAdapter;

    public Fragment setActivity(AppCompatActivity activity){
        this.activity=activity;
        return this;
    }

    public IngredientFragment() {
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
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        findViews(view);
        loadData();
        
        return view;
    }



    private void findViews(View view) {
        ingredients_RECYC = view.findViewById(R.id.ingredients_RECYC);
    }




    private void loadData() {
    }



}