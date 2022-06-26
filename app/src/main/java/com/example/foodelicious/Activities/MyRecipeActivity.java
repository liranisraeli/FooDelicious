package com.example.foodelicious.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodelicious.Adapters.IngredientAdapter;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRecipeActivity extends AppCompatActivity {

    private RecyclerView recipe_RECYC_ingredients;

    //Firebase
    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    public static final String KEY_USERS = "users";

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
    private String currentIngredientName;
    private String currentRecipeName;
    private String currentCategoryName;

    private ArrayList<Ingredient> myIngredients = new ArrayList();
    IngredientAdapter ingredientAdapter = new IngredientAdapter(this, myIngredients);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);

        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentCategoryName = bundle.getString("currentCategoryName");
            currentRecipeName = bundle.getString("currentRecipeName");
        } else {
            this.bundle = new Bundle();
        }

        findViews();
        recipe_RECYC_ingredients.setLayoutManager(new LinearLayoutManager(this));
        recipe_RECYC_ingredients.setHasFixedSize(true);
        recipe_RECYC_ingredients.setAdapter(ingredientAdapter);


        initButtons();
        updateUI();
    }



    private void updateUI() {
        DatabaseReference listRef = realtimeDB.getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories").child(currentCategoryName).child("recipes").child(currentRecipeName).child("ingredient");
        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myIngredients = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        String name = child.child("name").getValue(String.class);
                        Ingredient tempIngredient = new Ingredient();
                        tempIngredient.setName(name);
                        myIngredients.add(tempIngredient);
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
        ingredientAdapter.setIngredientlistener(new IngredientAdapter.IngredientListener() {
            @Override
            public void clicked(Ingredient ingredient, int position) {
                currentIngredientName = ingredient.getName();
                //todo check
                Intent intent = new Intent(MyRecipeActivity.this, MyRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentIngredientName", currentIngredientName);
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
            @Override
            public void plus(Ingredient ingredient, int position) {
                ingredient.addAmount(1);
                recipe_RECYC_ingredients.getAdapter().notifyItemChanged(position);
            }
            @Override
            public void minus(Ingredient ingredient, int position) {
                ingredient.addAmount(-1);
                recipe_RECYC_ingredients.getAdapter().notifyItemChanged(position);
            }
            @Override
            public void longClick(Ingredient ingredient, int position) {
                delete(position, ingredient.getName());
            }
        });
    }


    private void delete(int position, String name) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= realtimeDB.getReference(KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("recipes").child(currentIngredientName).child("ingredients");
        // we are use add listerner
        // for event listener method
        // which is called with query.
        Query query=dbref.child(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // remove the value at reference
                snapshot.getRef().removeValue();
                myIngredients.remove(position);
                ingredientAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
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

        recipe_RECYC_ingredients = findViewById(R.id.recipe_RECYC_ingredients);
    }

    private void initButtons() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(MyRecipeActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.item2:
                        AuthUI.getInstance()
                                .signOut(MyRecipeActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(MyRecipeActivity.this, Activity_Login.class));
                                        Toast.makeText(MyRecipeActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
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
                //todo check this
                Intent intent = new Intent(MyRecipeActivity.this,MyRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentRecipeName",currentIngredientName);
                intent.putExtra("Bundle",bundle);
                startActivity(intent);
                finish();
            }
        });
    }

}
