package com.example.foodelicious.Objects;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyUser {
    private String uid;
    private String name;
    private String phoneNumber;
    private String profileImgUrl;

    public MyUser() { }

    public MyUser(String uid, String name, String phoneNumber, String profileImgUrl) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImgUrl = profileImgUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public MyUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MyUser setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public MyUser setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
        return this;
    }



    @Override
    public String toString() {
        return "MyUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                '}';
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("phoneNumber", phoneNumber);
        result.put("profileImgUrl", profileImgUrl);
        return result;
    }
}
