package com.example.foodelicious.Objects;

public class MyRecipe {

    private String name;
    private String methodSteps;
    private String recipeUid;
    private String category;
    private boolean favorite;

    public  MyRecipe() {
    }

    public MyRecipe(String name, String methodSteps, String recipeUid, String category, boolean favorite) {
        this.name = name;
        this.methodSteps = methodSteps;
        this.recipeUid = recipeUid;
        this.category = category;
        this.favorite = favorite;
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

    public String getRecipeUid() {
        return recipeUid;
    }

    public void setRecipeUid(String recipeUid) {
        this.recipeUid = recipeUid;
    }

}
