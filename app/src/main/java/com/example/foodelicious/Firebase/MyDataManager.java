package com.example.foodelicious.Firebase;



import android.content.Context;

import androidx.annotation.NonNull;

import com.example.foodelicious.CallBacks.CallBackCreateRecipe;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.Objects.MyUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class MyDataManager {

    private static MyDataManager single_instance = null;
    private Context context;

    private CallBackCreateRecipe callBackCreateRecipe;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseStorage storage;
    private final FirebaseDatabase realTimeDB;
    public static final String KEY_USERS = "users";
    public static final String KEY_RECIPES = "recipes";

    private MyUser currentUser;
    private String currentListUid;
    private String currentListCreator;
    private String currentListTitle;





    private ArrayList<MyRecipe> myRecipes;
    private MyRecipe currentRecipe;
    private ArrayList<MyCategory> myCategories;
    private ArrayList<String> categoriesName;
    private String currentCategoryName;
    private ArrayList<MyRecipe> filteredCurrentRecipes;
    private String path;
    private String myRecipesPath;

    public CallBackCreateRecipe getCallBackCreateRecipe() {
        return callBackCreateRecipe;
    }

    public MyDataManager setCallBackCreateRecipe(CallBackCreateRecipe callBackCreateRecipe) {
        this.callBackCreateRecipe = callBackCreateRecipe;
        return this;
    }

    private MyDataManager(Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        realTimeDB = FirebaseDatabase.getInstance();
        this.context = context;
        buildArrays();
    }




    public static MyDataManager getInstance() {
        return single_instance;
    }

    public static MyDataManager initHelper(Context context) {
        if (single_instance == null) {
            single_instance = new MyDataManager(context);
        }
        return single_instance;
    }

    public String getPath() {
        return path;
    }

    public MyDataManager setPath(String path) {
        this.path = path;
        return this;
    }

    public ArrayList<MyRecipe> getFilteredCurrentRecipes() {
        return filteredCurrentRecipes;
    }

    public MyDataManager setFilteredCurrentRecipes(ArrayList<MyRecipe> filteredCurrentRecipes) {
        this.filteredCurrentRecipes = filteredCurrentRecipes;
        return this;
    }

    public ArrayList<MyRecipe> getMyRecipes() {
        return myRecipes;
    }

    public MyDataManager setMyRecipes(ArrayList<MyRecipe> myRecipes) {
        this.myRecipes = myRecipes;
        return this;
    }

    public String getCurrentCategoryName() {
        return currentCategoryName;
    }

    public MyDataManager setCurrentCategoryName(String currentCategoryName) {
        this.currentCategoryName = currentCategoryName;
        return this;
    }

    private void buildArrays() {
        myRecipes = new ArrayList<MyRecipe>();
        categoriesName = new ArrayList<String>();
        myCategories = new ArrayList<MyCategory>();
        MyCategory categoryFish = new MyCategory();
        categoryFish.setTitle("Fish").setImage_cover("https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fic_fish.jpg?alt=media&token=aa16792a-5904-4bc6-a0ec-18b58f61e755");
        myCategories.add(categoryFish);
        MyCategory categoryMeat = new MyCategory();
        categoryMeat.setTitle("Meat").setImage_cover("https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fic_meat.jpg?alt=media&token=95281e1e-40ff-45be-8579-94f1f1fd71ee");
        myCategories.add(categoryMeat);
        MyCategory categoryPastas = new MyCategory();
        categoryPastas.setTitle("Pastas").setImage_cover("https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fic_pastas.jpg?alt=media&token=c7cb02c2-f6cb-4da1-be82-dddbfbbdf0ca");
        myCategories.add(categoryPastas);
        MyCategory categoryPizzas = new MyCategory();
        categoryPizzas.setTitle("Pizzas").setImage_cover("https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fic_pizza.jpg?alt=media&token=c87bd811-480c-4e90-8c63-6726a78fc865");
        myCategories.add(categoryPizzas);
        MyCategory categoryPies = new MyCategory();
        categoryPies.setTitle("Pies").setImage_cover("https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fic_pie.jpg?alt=media&token=6a3e1a30-b100-4a2b-bc87-1d835fa50e8a");
        myCategories.add(categoryPies);
        MyCategory categoryDesserts = new MyCategory();
        categoryDesserts.setTitle("Desserts").setImage_cover("https://firebasestorage.googleapis.com/v0/b/foodelicious-8c630.appspot.com/o/default_pictures%2Fic_desserts.jpg?alt=media&token=333066e1-13dc-48fb-b243-ce7ddf1269f1");
        myCategories.add(categoryDesserts);
        for (int i=0;i<myCategories.size();i++){
            categoriesName.add(myCategories.get(i).getTitle());
        }
    }

    public ArrayList<String> getCategoriesName() {
        return categoriesName;
    }

    public ArrayList<MyCategory> getMyCategories() {
        return myCategories;
    }

    public MyDataManager setMyCategories(ArrayList<MyCategory> myCategories) {
        this.myCategories = myCategories;
        return this;
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

    public MyRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public MyDataManager setCurrentRecipe(MyRecipe currentRecipe) {
        this.currentRecipe = currentRecipe;
        return this;
    }

    public String getMyRecipesPath() {
        return myRecipesPath;
    }

    public MyDataManager setMyRecipesPath(String myRecipesPath) {
        this.myRecipesPath = myRecipesPath;
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

    public void addNewRecipe(MyRecipe recipe) {
        DatabaseReference myRef = realTimeDB.getReference(KEY_RECIPES).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories").child(recipe.getCategory()).child(recipe.getRecipeUid());
        myRef.child("name").setValue(recipe.getName());
        myRef.child("method steps").setValue(recipe.getMethodSteps());
        myRef.child("favorites").setValue(recipe.isFavorite());
        myRef.child("category").setValue(recipe.getCategory());
        myRef.child("ingredients").setValue(recipe.getIngredients());
    }

//    public void loadRecipes(String uid) {
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<MyRecipe> recipes = new ArrayList<>();
//                if (snapshot.exists()) {
//                    for (DataSnapshot child : snapshot.getChildren()) {
//                        try {
//                            MyRecipe recipe = child.getValue(MyRecipe.class);
//                            recipes.add(recipe);
//                        } catch (Exception ex) {
//                        }
//                    }
//                }
//                myRecipes = recipes;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//    }
}






