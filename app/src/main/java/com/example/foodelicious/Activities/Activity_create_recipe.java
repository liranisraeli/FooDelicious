package com.example.foodelicious.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_create_recipe extends AppCompatActivity {

    private TextInputLayout form_EDT_name;
    private TextInputLayout editItem_EDT_notes;
    private TextInputLayout createRecipe_TIN_category;
    private MaterialButton panel_BTN_create;
    private AutoCompleteTextView category_AutoCompleteTextViewCategory;
    private String categorySelected;
    private ArrayAdapter<MyCategory> categoryAdapter;


    public static final String KEY_RECIPES = "recipes";

    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private Bundle bundle;
    private String currentCategoryName;

    public static final String KEY_USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentCategoryName= bundle.getString("currentCategoryName");
        } else {
            this.bundle = new Bundle();
        }
        findViews();
//        configEditFields();
        initButtons();
    }

    private void findViews() {
        form_EDT_name = findViewById(R.id.form_EDT_name);
        editItem_EDT_notes = findViewById(R.id.editItem_EDT_notes);
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
        createRecipe_TIN_category = findViewById(R.id.createRecipe_TIN_category);
        category_AutoCompleteTextViewCategory = findViewById(R.id.category_AutoCompleteTextViewCategory);
        categoryAdapter = new ArrayAdapter<MyCategory>(this,R.layout.drop_down_category,dataManager.getMyCategories());
        category_AutoCompleteTextViewCategory.setAdapter(categoryAdapter);
    }



    private void initButtons() {
        category_AutoCompleteTextViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categorySelected = adapterView.getItemAtPosition(i).toString();
            }
        });

        panel_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecipe tempRecipe = new MyRecipe();
                tempRecipe.setName(form_EDT_name.getEditText().getText().toString());
                tempRecipe.setMethodSteps(editItem_EDT_notes.getEditText().getText().toString());
                tempRecipe.setCategory(categorySelected);
                tempRecipe.setFavorite(false);
                DatabaseReference myRef = realtimeDB.getReference(KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories").child(currentCategoryName).child("recipes");
                myRef.child(tempRecipe.getName()).child("name").setValue(tempRecipe.getName());
                dataManager.addNewRecipe(tempRecipe);
                startActivity(new Intent(Activity_create_recipe.this, MyAllRecipesActivity.class));
                finish();
            }
        });


    }



}