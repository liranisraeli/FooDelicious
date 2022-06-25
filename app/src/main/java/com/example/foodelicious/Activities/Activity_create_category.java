package com.example.foodelicious.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyCategory;
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

public class Activity_create_category extends AppCompatActivity {

    private FloatingActionButton createList_FAB_profile_pic;
    private ShapeableImageView createList_IMG_user;
    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_create;
    private CircularProgressIndicator createList_BAR_progress;

    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();


    public static final String KEY_USERS = "users";
    public static final String KEY_CATEGORIES = "categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        findViews();
        initButtons();
    }

    private void findViews() {
        createList_FAB_profile_pic = findViewById(R.id.createCat_FAB_profile_pic);
        createList_IMG_user = findViewById(R.id.createCat_IMG_user);
        form_EDT_name = findViewById(R.id.form_EDT_name);
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
        createList_BAR_progress = findViewById(R.id.createCat_BAR_progress);
    }

    private void initButtons() {
        createList_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });

        createList_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });

        panel_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCategory tempCategory = new MyCategory();
                tempCategory.setTitle(form_EDT_name.getEditText().getText().toString());
                DatabaseReference myRef = realtimeDB.getReference(KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories");
                myRef.child(tempCategory.getTitle()).child("image").setValue(tempCategory.getImage_cover());
                myRef.child(tempCategory.getTitle()).child("title").setValue(tempCategory.getTitle());
                myRef.child(tempCategory.getTitle()).child("itemsCounter").setValue(0);
                startActivity(new Intent(Activity_create_category.this, MainActivity.class));
                finish();
            }
        });
    }


    /**
     * Load ImagePicker activity to choose the category cover
     */
    private void choseCover() {
        ImagePicker.with(Activity_create_category.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .crop(2f, 1f)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    private void storeListInDB(MyCategory listToStore) {
        DatabaseReference myRef = realtimeDB.getReference(KEY_CATEGORIES).child(listToStore.getCategoryUid());
        myRef.child("title").setValue(listToStore.getTitle());
        myRef.child("image_cover").setValue(listToStore.getImage_cover());
        startActivity(new Intent(Activity_create_category.this, MainActivity.class));
        finish();
    }

    }



