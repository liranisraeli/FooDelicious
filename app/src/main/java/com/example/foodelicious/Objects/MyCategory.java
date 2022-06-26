package com.example.foodelicious.Objects;

import java.util.ArrayList;
import java.util.UUID;

public class MyCategory {

    private String listUid;
    private String title = "";
    private int items_Counter = 0;
    private String image_cover = "https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fimg_default_category.png?alt=media&token=b9144ce6-36dc-47d8-88ff-4fc0d1dc2db3";
    private ArrayList<String> itemsUid;
    private String creatorUid = "";

    public MyCategory() { }


    public MyCategory(String title, String creatorUid) {
        this.listUid = UUID.randomUUID().toString();
        this.title = title;
        this.image_cover = "https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fimg_default_category.png?alt=media&token=b9144ce6-36dc-47d8-88ff-4fc0d1dc2db3";
        this.items_Counter = items_Counter;
        this.creatorUid = creatorUid;
        this.itemsUid = new ArrayList<>();
    }

    public String getCategoryUid() {
        return listUid;
    }

    public String getTitle() {
        return title;
    }

    public MyCategory setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getItems_Counter() {
        return items_Counter;
    }

    public MyCategory setItems_Counter(int items_Counter) {
        this.items_Counter = items_Counter;
        return this;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public MyCategory setImage_cover(String image_cover) {
        this.image_cover = image_cover;
        return this;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public ArrayList<String> getItemsUid() {
        return itemsUid;
    }


}