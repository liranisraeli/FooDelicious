package com.example.foodelicious.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.foodelicious.Firebase.MyDataManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class Activity_Login extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private ImageView login_IMG_logo;
    private MaterialButton login_BTN_login;
    private final MyDataManager dataManager = MyDataManager.getInstance();

    //Authentication
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Authentication
        mAuth = FirebaseAuth.getInstance(); //Init
        currentUser = mAuth.getCurrentUser();

        //check if there is user that login with this number
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            loadUserFromDB(); //there is user that sign
        }
        findViews();
        initButtons();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
    }


    private void loadUserFromDB() {
        //Store the user UID by Phone number
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference("users").child(user.getUid());
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    MyUser user = dataSnapshot.getValue(MyUser.class);
                    dataManager.setCurrentUser(user); // OR dataManager.getInstance().setCurrentUser(user);
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
        login_IMG_logo = findViewById(R.id.login_IMG_logo);
        login_BTN_login = findViewById(R.id.login_BTN_login);
    }

    public void initButtons() {
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
}
