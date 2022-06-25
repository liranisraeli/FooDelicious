package com.example.foodelicious.Objects;

public class MyRecipe {

    private String name;
    private String recipeUid;

    public  MyRecipe() {

    }

    public MyRecipe(String name, String recipeUid) {
        this.name = name;
        this.recipeUid = recipeUid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipeUid() {
        return recipeUid;
    }

    public void setRecipeUid(String recipeUid) {
        this.recipeUid = recipeUid;
    }

}
