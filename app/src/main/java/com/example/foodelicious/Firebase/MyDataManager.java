package com.example.foodelicious.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodelicious.Objects.MyUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class MyDataManager {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseStorage storage;
    private final FirebaseDatabase realTimeDB;
    public static final String KEY_USERS = "users";

    private MyUser currentUser;
    private String currentListUid;
    private String currentListCreator;
    private String currentListTitle;

    private static MyDataManager single_instance = null;

    private MyDataManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        realTimeDB = FirebaseDatabase.getInstance();
    }

    public static MyDataManager getInstance() {
        return single_instance;
    }

    public static MyDataManager initHelper() {
        if (single_instance == null) {
            single_instance = new MyDataManager();
        }
        return single_instance;
    }

    //Firebase Getters

    public FirebaseDatabase getRealTimeDB() {
        return realTimeDB;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }


    //My Data Base Helpers
    public MyUser getCurrentUser() {
        return currentUser;
    }

    public MyDataManager setCurrentUser(MyUser currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public String getCurrentListUid() {
        return currentListUid;
    }

    public void setCurrentListUid(String currentListUid) {
        this.currentListUid = currentListUid;
    }

    public String getCurrentListTitle() {
        return currentListTitle;
    }

    public void setCurrentListTitle(String currentListTitle) {
        this.currentListTitle = currentListTitle;
    }

    public void setCurrentListCreator(String creatorUid) {
        this.currentListCreator = creatorUid;
    }

    public String getCurrentListCreator() {
        return currentListCreator;
    }




    }






