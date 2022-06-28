package com.example.foodelicious.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyUser;
import com.example.foodelicious.R;
import com.google.android.material.textview.MaterialTextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private AppCompatActivity activity;
    private CircleImageView Profile_IMG_User;
    private MaterialTextView Profile_LBL_UserName;
    private MaterialTextView Profile_LBL_UserPhoneNumber;


    private final MyDataManager dataManager = MyDataManager.getInstance();

    public Fragment setActivity(AppCompatActivity activity){
        this.activity=activity;
        return this;
    }

    public ProfileFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        loadData();



        return view;
    }




    private void findViews(View view) {
        Profile_IMG_User=view.findViewById(R.id.Profile_IMG_User);
        Profile_LBL_UserName=view.findViewById(R.id.Profile_LBL_UserName);
        Profile_LBL_UserPhoneNumber=view.findViewById(R.id.Profile_LBL_UserPhoneNumber);
    }

    private void loadData() {
        MyUser user = dataManager.getCurrentUser();
        Glide.with(this).load(user.getProfileImgUrl()).into(Profile_IMG_User);
        Profile_LBL_UserName.setText(user.getName());
        Profile_LBL_UserPhoneNumber.setText(user.getPhoneNumber());
    }

}