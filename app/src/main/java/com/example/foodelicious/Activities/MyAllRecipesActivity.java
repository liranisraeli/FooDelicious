package com.example.foodelicious.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.foodelicious.Adapters.RecipeAdapter;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAllRecipesActivity extends AppCompatActivity {

    private RecyclerView category_RECYC_recipes;

    //Firebase
    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private FloatingActionButton toolbar_FAB_add;
    //nav
    private NavigationView nav_view;
    private View header;
    private FloatingActionButton navigation_header_container_FAB_profile_pic;
    private MaterialTextView header_TXT_username;
    private CircleImageView header_IMG_user;
    private CircularProgressIndicator header_BAR_progress;
    public static final String KEY_PROFILE_PICTURES = "profile_pictures";

    private Bundle bundle;
    private String currentCategoryName;
    private String currentRecipeName;
    private ArrayList<MyRecipe> myRecipes = new ArrayList();
    RecipeAdapter recipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all_recipes);

        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            this.currentCategoryName= bundle.getString("currentCategoryName");
        } else {
            this.bundle = new Bundle();
        }
        findViews();
        initButtons();
        updateUI(currentCategoryName);

    }

    private void updateUI(String str) {
        DatabaseReference CategoryRef = realtimeDB.getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories").child(str).child("recipes");
        Log.d("ppf", currentCategoryName +" 2");

        CategoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRecipes = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        String name = child.child("name").getValue(String.class);
//                        String methodSteps = child.child("methods_steps").getValue(String.class);
                        MyRecipe tempRecipe = new MyRecipe();
                        tempRecipe.setName(name);;
//                        tempRecipe.setMethodSteps(methodSteps);;
                        myRecipes.add(tempRecipe);
                    } catch (Exception ex) {}
                }
                initAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initAdapter() {
        recipeAdapter = new RecipeAdapter(this, myRecipes, new RecipeAdapter.RecipeListener() {
            @Override
            public void clicked(MyRecipe recipe, int position) {
                currentRecipeName = recipe.getName();
                Intent intent = new Intent(MyAllRecipesActivity.this,MyRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentRecipeName", currentRecipeName);
                //todo check
                bundle.putString("currentCategoryName", currentCategoryName);
                Log.d("ppc", currentRecipeName + " 3");

                intent.putExtra("Bundle",bundle);
                startActivity(intent);
            }
        });
        category_RECYC_recipes.setLayoutManager(new GridLayoutManager(this,1));
        category_RECYC_recipes.setAdapter(recipeAdapter);
    }


    private void findViews() {
        //nav
        nav_view = findViewById(R.id.nav_view);
        header = nav_view.getHeaderView(0);
        navigation_header_container_FAB_profile_pic = (FloatingActionButton) header.findViewById(R.id.navigation_header_container_FAB_profile_pic);
        header_TXT_username = header.findViewById(R.id.header_TXT_username);
        header_IMG_user = header.findViewById(R.id.header_IMG_user);
        header_BAR_progress = header.findViewById(R.id.header_BAR_progress);
        toolbar_FAB_add = findViewById(R.id.toolbar_FAB_add);

        category_RECYC_recipes = findViewById(R.id.category_RECYC_recipes);
    }

    private void initButtons() {

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(MyAllRecipesActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.item2:
                        AuthUI.getInstance()
                                .signOut(MyAllRecipesActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(MyAllRecipesActivity.this, Activity_Login.class));
                                        Toast.makeText(MyAllRecipesActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                        break;
                }
                return true;
            }

        });
//
//        panel_AppBar_bottom.setNavigationOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                drawer_layout.openDrawer(drawer_layout.getForegroundGravity());
//            }
//        });

        navigation_header_container_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  editPic();
            }
        });

        header_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // editPic();
            }
        });

        toolbar_FAB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAllRecipesActivity.this,Activity_create_recipe.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentCategoryName", currentCategoryName);
                bundle.putString("currentRecipeName", currentRecipeName);
                intent.putExtra("Bundle",bundle);
                startActivity(intent);
                finish();
            }
        });
    }

}
