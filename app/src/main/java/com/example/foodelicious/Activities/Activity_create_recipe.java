package com.example.foodelicious.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_create_recipe extends AppCompatActivity {

    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_create;
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
        initButtons();
    }

    private void findViews() {
        form_EDT_name = findViewById(R.id.form_EDT_name);
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
    }

    private void initButtons() {
        panel_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecipe tempRecipe = new MyRecipe();
                tempRecipe.setName(form_EDT_name.getEditText().getText().toString());
                DatabaseReference myRef = realtimeDB.getReference(KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories").child(currentCategoryName).child("recipes");
                myRef.child(tempRecipe.getName()).child("name").setValue(tempRecipe.getName());
                startActivity(new Intent(Activity_create_recipe.this, MyCategoryActivity.class));
                finish();
            }
        });
    }

    private void storeListInDB(MyCategory listToStore) {
        DatabaseReference myRef = realtimeDB.getReference(KEY_RECIPES).child(listToStore.getCategoryUid());
        myRef.child("name").setValue(listToStore.getTitle());
        startActivity(new Intent(Activity_create_recipe.this, MyCategoryActivity.class));
        finish();
    }


}