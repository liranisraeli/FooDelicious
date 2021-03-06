package com.example.foodelicious.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyUser;
import com.example.foodelicious.R;
import android.content.Intent;
import android.view.View;


import com.example.foodelicious.Validator;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
public class Activity_SignUp extends AppCompatActivity {


    //Profile Picture
    private FloatingActionButton signup_FAB_profile_pic;
    private CircleImageView signup_IMG_user;
    private String myDownloadUri= "https://firebasestorage.googleapis.com/v0/b/superme-e69d5.appspot.com/o/images%2Fimg_profile_pic.JPG?alt=media&token=5970cec0-9663-4ddd-9395-ef2791ad938d";

    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_update;

    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private MyUser tempMyUser;
    Validator validatorName;

    //Storage keys
    public static final String KEY_PROFILE_PICTURES = "profile_pictures";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        initValidator();
        initButtons();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dataManager.getFirebaseAuth().signOut();
    }


    private void findViews(){
        signup_FAB_profile_pic = findViewById(R.id.signup_FAB_profile_pic);
        signup_IMG_user = findViewById(R.id.signup_IMG_user);
        form_EDT_name = findViewById(R.id.form_EDT_name);
        panel_BTN_update = findViewById(R.id.panel_BTN_update);
    }


    private void initButtons(){
        //Profile Picture
        signup_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });

        signup_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });


        panel_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatorName.validateIt()){
                    String userName = form_EDT_name.getEditText().getText().toString();
                    String userID = dataManager.getFirebaseAuth().getCurrentUser().getUid();
                    String userPhone = dataManager.getFirebaseAuth().getCurrentUser().getPhoneNumber();
                    tempMyUser = new MyUser(userID, userName, userPhone,myDownloadUri); //create user
                }
                dataManager.setCurrentUser(tempMyUser);
                storeUserInDB(tempMyUser);
    }

});
    }

    private void initValidator() {
        validatorName=Validator.Builder.make(form_EDT_name)
                .addWatcher(new Validator.Watcher_StringEmpty("Name Cannot Be Empty"))
                .addWatcher(new Validator.Watcher_String("Name Contains Only Characters"))
                .build();

    }

    private void storeUserInDB(MyUser userToStore) {
        //Store the user UID by Phone number
        DatabaseReference myRef = realtimeDB.getReference("users").child(userToStore.getUid());
        myRef.child("name").setValue(userToStore.getName());
        myRef.child("phoneNumber").setValue(userToStore.getPhoneNumber());
        myRef.child("profileImgUrl").setValue(userToStore.getProfileImgUrl());
        startActivity(new Intent(Activity_SignUp.this, MainActivity.class));
        finish();
    }




    /**
     * Load ImagePicker activity to choose the category cover
     */
    private void choseCover() {
        ImagePicker.with(Activity_SignUp.this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .crop(1f, 1f)
                .maxResultSize(1080, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    /**
     *Results From ImagePicker will be catch here
     * will place the image in the relevant Image View
     * Right after that, will catch the image bytes back from the view and store them in the Firebase Storage.
     * After successful upload will update the Object Url Field
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //View Indicates the process of the image uploading by Disabling the button
        panel_BTN_update.setEnabled(false);

        //Reference to the exact path where we want the image to be store in Storage
        StorageReference userRef = dataManager.getStorage()
                .getReference()
                .child(KEY_PROFILE_PICTURES)
                .child(dataManager.getFirebaseAuth().getCurrentUser().getUid());

        //Get URI Data and place it in ImageView
        Uri uri = data.getData();
        signup_IMG_user.setImageURI(uri);

        //Get the data from an ImageView as bytes
        signup_IMG_user.setDrawingCacheEnabled(true);
        signup_IMG_user.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) signup_IMG_user.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //Start The upload task
        UploadTask uploadTask = userRef.putBytes(bytes);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    //If upload was successful, We want to get the image url from the storage
                    userRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Set the profile URL to the object we created
                            myDownloadUri = uri.toString();
                            //View Indicates the process of the image uploading Done by making the button Enabled
                            panel_BTN_update.setEnabled(true);
                        }
                    });
                }
            }
        });

    }



}
