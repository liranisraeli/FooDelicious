package com.example.foodelicious.Objects;

import java.util.ArrayList;

public class MyRecipe {

    private String name;
    private String methodSteps;
    private int recipeUid;
    private String category;
    private boolean favorite;
    private ArrayList<Ingredient> ingredients;

    public  MyRecipe() {
    }

    public MyRecipe(String name, String methodSteps, int recipeUid, String category, boolean favorite, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.methodSteps = methodSteps;
        this.recipeUid = recipeUid;
        this.category = category;
        this.favorite = favorite;
        this.ingredients = ingredients;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public MyRecipe setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public MyRecipe setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMethodSteps() {
        return methodSteps;
    }

    public void setMethodSteps(String methodSteps) {
        this.methodSteps = methodSteps;
    }

    public int getRecipeUid() {
        return recipeUid;
    }

    public MyRecipe setRecipeUid(int recipeUid) {
        this.recipeUid = recipeUid;
        return this;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public MyRecipe setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }
}
