package com.example.foodelicious.Objects;

public class MyRecipe {

    private String name;
    private String methodSteps;
    private String recipeUid;

    public  MyRecipe() {

    }

    public MyRecipe(String name,String methodSteps, String recipeUid) {
        this.name = name;
        this.methodSteps = methodSteps;
        this.recipeUid = recipeUid;
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
