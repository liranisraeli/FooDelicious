package com.example.foodelicious.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.Objects.MyUser;
import com.example.foodelicious.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Activity_Login extends AppCompatActivity {


    private MaterialButton login_BTN_login;
    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    //Authentication



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Authentication



        findViews();
        initButtons();

    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );


    private void loadUserFromDB() {
        //Store the user UID by Phone number

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase fdb=FirebaseDatabase.getInstance();
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference("users").child(user.getUid());
//        DatabaseReference myRecipeRef = dataManager.getRealTimeDB().getReference("recipes").child(user.getUid());
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    MyUser myUser = dataSnapshot.getValue(MyUser.class);
                    dataManager.setCurrentUser(myUser);
                    // OR dataManager.getInstance().setCurrentUser(myUser);
                     // OR dataManager.getInstance().setCurrentUser(myUser);
                    startActivity(new Intent(Activity_Login.this,MainActivity.class));
                }
                else{
                    //no such document
                    startActivity(new Intent(Activity_Login.this,Activity_SignUp.class));
                }
                finish();
            }
        });

    }



    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
//            login_BTN_login.setVisibility(View.INVISIBLE);
            loadUserFromDB(); //now someone is sign
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private void login(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_logo)
                .build();
        //signInIntent.addFlags(PendingIntent.FLAG_IMMUTABLE);
        signInLauncher.launch(signInIntent);
    }

    public void findViews(){
        login_BTN_login = findViewById(R.id.login_BTN_login);
    }

    public void initButtons() {
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if there is user that login with this number
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    loadUserFromDB();
                    updateUI();
                    //there is user that sign
                }else {
                    login();
                }
            }
        });
    }

    private void updateUI() {
        //todo check if load
        ArrayList<MyRecipe> myRecipes = new ArrayList();
        DatabaseReference recipceRef = realtimeDB.getReference("recipes/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories");
        for (int i = 0; i < dataManager.getCategoriesName().size(); i++) {
            DatabaseReference categoryRef = recipceRef.child(dataManager.getCategoriesName().get(i));
            categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        try {
                            MyRecipe tempRecipe = new MyRecipe();
                            recipeIngredients = loadIngredients(child);
                            tempRecipe.setRecipeUid(child.getKey());
                            String name = child.child("name").getValue(String.class);
                            String methodSteps = child.child("method steps").getValue(String.class);
                            boolean isFavorite = child.child("favorites").getValue(Boolean.class);
                            String category = child.child("category").getValue(String.class);
                            tempRecipe.setName(name);
                            tempRecipe.setMethodSteps(methodSteps);
                            tempRecipe.setFavorite(isFavorite);
                            tempRecipe.setCategory(category);
                            tempRecipe.setIngredients(recipeIngredients);
                            getCategory(category);
                            myRecipes.add(tempRecipe);

                        } catch (Exception ex) {
                        }
                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        dataManager.setMyRecipes(myRecipes);
    }


    private void getCategory(String category) {
        for(int i=0;i<dataManager.getMyCategories().size();i++){
            if(dataManager.getMyCategories().get(i).getTitle().equals(category)){
                dataManager.getMyCategories().get(i).setItems_Counter(dataManager.getMyCategories().get(i).getItems_Counter()+1);
            }
        }
    }

    private  ArrayList<Ingredient> loadIngredients(DataSnapshot child) {
        ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
        DatabaseReference ingredientRef = child.child("ingredients").getRef();
        ingredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapChild : snapshot.getChildren()) {
                    try {
                        Ingredient ingredient = new Ingredient();
                        ingredient.setName(dataSnapChild.child("name").getValue().toString());
                        ingredient.setAmount(Integer.parseInt(dataSnapChild.child("amount").getValue().toString()));
                        recipeIngredients.add(ingredient);
                    } catch (Exception ex) {
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return recipeIngredients;
    }


}
