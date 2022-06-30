package com.example.foodelicious.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodelicious.Adapters.CategoriesAdapter;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Fragments.CategoriesFragment;
import com.example.foodelicious.Fragments.FavoritesFragment;
import com.example.foodelicious.Fragments.ProfileFragment;
import com.example.foodelicious.Fragments.RecipesFragment;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.Objects.MyUser;
import com.example.foodelicious.R;
import com.firebase.ui.auth.AuthUI;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {




    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final MyUser currentUser = dataManager.getCurrentUser();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    //bottom
    private FloatingActionButton toolbar_FAB_add;
    private BottomNavigationView panel_BottomNavigationView;
    private BottomAppBar panel_AppBar_bottom;


    //nav
    private MaterialToolbar panel_Toolbar_Top;
    private NavigationView nav_view;
    private View header;
    //    private FloatingActionButton navigation_header_container_FAB_profile_pic;
    private MaterialTextView header_TXT_username;
    private CircleImageView header_IMG_user;
    //    private CircularProgressIndicator header_BAR_progress;
    public static final String KEY_PROFILE_PICTURES = "profile_pictures";

    //Fragment
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static final int Profile = 0, All_Recipes = 1, Categories = 2, Favorites = 3;
    private final int SIZE = 4;
    private Fragment[] panel_fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(panel_AppBar_bottom);
        setSupportActionBar(panel_Toolbar_Top);


        findViews();
        initButtons();
        setFragments();
        updateUser();
        dataManager.setPath("main");
        panel_Toolbar_Top.setTitle("Profile");
        replaceFragments(panel_fragments[Profile]);
    }


    private void findViews() {
        panel_Toolbar_Top = findViewById(R.id.panel_Toolbar_Top);
        panel_AppBar_bottom = findViewById(R.id.panel_AppBar_bottom);
        toolbar_FAB_add = findViewById(R.id.toolbar_FAB_add);
        panel_BottomNavigationView = findViewById(R.id.panel_BottomNavigationView);
        panel_BottomNavigationView.setBackground(null);
        panel_Toolbar_Top.setNavigationIconTint(0);

        //nav
        nav_view = findViewById(R.id.nav_view);
        header = nav_view.getHeaderView(0);
//        navigation_header_container_FAB_profile_pic =(FloatingActionButton)header.findViewById(R.id.navigation_header_container_FAB_profile_pic);
        header_TXT_username = header.findViewById(R.id.header_TXT_username);
        header_IMG_user = header.findViewById(R.id.header_IMG_user);
//        header_BAR_progress =header.findViewById(R.id.header_BAR_progress);
    }


    private void setFragments() {
        panel_fragments = new Fragment[SIZE];
        panel_fragments[Profile] = new ProfileFragment().setActivity(this);
        panel_fragments[All_Recipes] = new RecipesFragment().setActivity(this);
        panel_fragments[Categories] = new CategoriesFragment().setActivity(this);
        panel_fragments[Favorites] = new FavoritesFragment().setActivity(this);

    }

    private void initButtons() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logOut:
                        AuthUI.getInstance()
                                .signOut(MainActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(MainActivity.this, Activity_Login.class));
                                        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                        break;
                }
                return true;
            }

        });

//        navigation_header_container_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changePic();
//            }
//        });

        header_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changePic();
            }
        });

        panel_BottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        panel_Toolbar_Top.setTitle("Profile");
                        replaceFragments(panel_fragments[Profile]);
                        break;
                    case R.id.allRecipes:
                        dataManager.setPath("main");
                        panel_Toolbar_Top.setTitle("All Recipes");
                        replaceFragments(panel_fragments[All_Recipes]);
                        break;
                    case R.id.allCategories:
                        dataManager.setPath("categories");
                        panel_Toolbar_Top.setTitle("Categories");
                        replaceFragments(panel_fragments[Categories]);
                        break;
                    case R.id.favorites:
                        panel_Toolbar_Top.setTitle("Favorites");
                        replaceFragments(panel_fragments[Favorites]);
                        break;
                }
                return true;
            }
        });


        toolbar_FAB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Activity_create_recipe.class));

            }
        });


    }


    /**
     * Load ImagePicker activity to choose the new profile picture
     */
    private void changePic() {
        ImagePicker.with(MainActivity.this)
                .crop(1f, 1f)            //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    private void updateUser() {
        DatabaseReference myRef = realtimeDB.getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                header_TXT_username.setText(snapshot.child("name").getValue(String.class));
                Uri myUri = Uri.parse(snapshot.child("profileImgUrl/").getValue(String.class));
                Glide.with(MainActivity.this)
                        .load(myUri)
                        .into(header_IMG_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    private void updateUI() {
//        DatabaseReference myRef = realtimeDB.getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                header_TXT_username.setText(snapshot.child("name").getValue(String.class));
//                Uri myUri = Uri.parse(snapshot.child("profileImgUrl/").getValue(String.class));
//                Glide.with(MainActivity.this)
//                        .load(myUri)
//                        .into(header_IMG_user);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        //todo check if load
//        ArrayList<MyRecipe> myRecipes = new ArrayList();
//        DatabaseReference recipceRef = realtimeDB.getReference("recipes/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories");
//        for (int i = 0; i < dataManager.getCategoriesName().size(); i++) {
//            DatabaseReference categoryRef = recipceRef.child(dataManager.getCategoriesName().get(i));
//            categoryRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
//                    for (DataSnapshot child : snapshot.getChildren()) {
//                        try {
//                            MyRecipe tempRecipe = new MyRecipe();
//                            recipeIngredients = loadIngredients(child);
//                            tempRecipe.setRecipeUid(Integer.parseInt(child.getKey()));
//                            String name = child.child("name").getValue(String.class);
//                            String methodSteps = child.child("method steps").getValue(String.class);
//                            boolean isFavorite = child.child("favorites").getValue(Boolean.class);
//                            String category = child.child("category").getValue(String.class);
//                            tempRecipe.setName(name);
//                            tempRecipe.setMethodSteps(methodSteps);
//                            tempRecipe.setFavorite(isFavorite);
//                            tempRecipe.setCategory(category);
//                            tempRecipe.setIngredients(recipeIngredients);
//                            getCategory(category);
//                            myRecipes.add(tempRecipe);
//
//                        } catch (Exception ex) {
//                        }
//                    }
//
//
//                }
//
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        dataManager.setMyRecipes(myRecipes);
//    }
//
//
//    private void getCategory(String category) {
//        for(int i=0;i<dataManager.getMyCategories().size();i++){
//            if(dataManager.getMyCategories().get(i).getTitle().equals(category)){
//                dataManager.getMyCategories().get(i).setItems_Counter(dataManager.getMyCategories().get(i).getItems_Counter()+1);
//            }
//        }
//    }
//
//    private  ArrayList<Ingredient> loadIngredients(DataSnapshot child) {
//        ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
//        Log.d("dora", child.child("ingredients").getRef().toString());
//        DatabaseReference ingredientRef = child.child("ingredients").getRef();
//        ingredientRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapChild : snapshot.getChildren()) {
//                    try {
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setName(dataSnapChild.child("name").getValue().toString());
//                        ingredient.setAmount(Integer.parseInt(dataSnapChild.child("amount").getValue().toString()));
//                        recipeIngredients.add(ingredient);
//                        Log.d("dora", dataSnapChild.child("name").getValue().toString());
//                    } catch (Exception ex) {
//                    }
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return recipeIngredients;
//    }


    /**
     * Results From ImagePicker will be catch here
     * will place the image in the relevant Image View
     * Right after that, will catch the image bytes back from the view and update them in the Firebase Storage.
     * After successful upload will update the Object Url Field
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        header_BAR_progress.setVisibility(View.VISIBLE);
        StorageReference storageRef = dataManager.getStorage().getReference().child(KEY_PROFILE_PICTURES).child(dataManager.getFirebaseAuth().getCurrentUser().getUid());
        if (data != null) {
            Uri uri = data.getData();
            header_IMG_user.setImageURI(uri);

            // Get the data from an ImageView as bytes
            header_IMG_user.setDrawingCacheEnabled(true);
            header_IMG_user.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) header_IMG_user.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();


            UploadTask uploadTask = storageRef.putBytes(bytes);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                MyUser userToStoreNav = dataManager.getCurrentUser();

                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        // If upload was successful, We want to get the image url from the storage
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                currentUser.setProfileImgUrl(uri.toString());
                                //Store the user UID by Phone number
                                DatabaseReference myRef = realtimeDB.getReference("users").child(userToStoreNav.getUid());
                                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Image Save in Database Successfully...", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
//                        header_BAR_progress.setVisibility(View.INVISIBLE);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Error: Null Data Received", Toast.LENGTH_SHORT).show();
        }
        // [END upload_memory]
    }

    private void replaceFragments(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.panel_Fragment, fragment, null);
        fragmentTransaction.commit();
    }


}

