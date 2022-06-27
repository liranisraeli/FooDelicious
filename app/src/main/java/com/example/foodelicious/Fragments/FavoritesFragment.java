package com.example.foodelicious.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.R;


public class FavoritesFragment extends Fragment {

    private AppCompatActivity activity;
    private final MyDataManager dataManager = MyDataManager.getInstance();

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);




        return view;
    }
}