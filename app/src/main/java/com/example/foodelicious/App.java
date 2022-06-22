package com.example.foodelicious;

import android.app.Application;
import com.example.foodelicious.Firebase.MyDataManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        //Initiate FireBase Managers
        MyDataManager.initHelper();
    }
}
