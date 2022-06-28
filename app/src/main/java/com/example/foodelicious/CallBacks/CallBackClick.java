package com.example.foodelicious.CallBacks;

import com.example.foodelicious.Objects.MyRecipe;

import java.util.ArrayList;

public interface CallBackClick {
    public void onClicked();
    public void favoriteClicked(int pos, MyRecipe recipe);
}
