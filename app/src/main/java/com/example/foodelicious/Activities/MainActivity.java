package com.example.foodelicious.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
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
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyUser;
import com.example.foodelicious.R;
import com.firebase.ui.auth.AuthUI;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MyCategory> myCategories;
    CategoriesAdapter categoriesAdapter;

    private RecyclerView main_RECYC_categories;
    private Bundle bundle;
    private String currentCategoryName;

    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final MyUser currentUser = dataManager.getCurrentUser();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private DrawerLayout drawer_layout;
    private FloatingActionButton toolbar_FAB_add;
    private BottomAppBar panel_AppBar_bottom;
    private MaterialToolbar panel_Toolbar_Top;

    //fragment
    private FragmentContainerView main_FRG_container;
    private FragmentManager fragmentManager;

    //nav
    private NavigationView nav_view;
    private View header;
    private FloatingActionButton navigation_header_container_FAB_profile_pic;
    private MaterialTextView header_TXT_username;
    private CircleImageView header_IMG_user;
    private CircularProgressIndicator header_BAR_progress;
    public static final String KEY_PROFILE_PICTURES = "profile_pictures";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(panel_AppBar_bottom);
        setSupportActionBar(panel_Toolbar_Top);

        buildArrays();
        findViews();
        initAdapter();
        initButtons();
        updateUI();
    }
    private void buildArrays() {
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
    }

    private void findViews() {
        drawer_layout = findViewById(R.id.drawer_layout);
        panel_Toolbar_Top = findViewById(R.id.panel_Toolbar_Top);
        panel_AppBar_bottom = findViewById(R.id.panel_AppBar_bottom);
        toolbar_FAB_add = findViewById(R.id.toolbar_FAB_add);

        main_RECYC_categories = findViewById(R.id.main_RECYC_categories);

        main_FRG_container = findViewById(R.id.main_FRG_container);

        //nav
        nav_view = findViewById(R.id.nav_view);
        header =nav_view.getHeaderView(0);
        navigation_header_container_FAB_profile_pic =(FloatingActionButton)header.findViewById(R.id.navigation_header_container_FAB_profile_pic);
        header_TXT_username =header.findViewById(R.id.header_TXT_username);
        header_IMG_user =header.findViewById(R.id.header_IMG_user);
        header_BAR_progress =header.findViewById(R.id.header_BAR_progress);
    }

    private void initButtons(){
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.item2:
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

        navigation_header_container_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
            }
        });

        header_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
            }
        });

        //todo check
        panel_Toolbar_Top.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo check
                Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_SHORT).show();

            }
        });

        panel_AppBar_bottom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(drawer_layout.getForegroundGravity());
            }
        });

        toolbar_FAB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Activity_create_category.class));

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


    private void updateUI() {
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

//        DatabaseReference categoryRef= realtimeDB.getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories");
//        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    try {
//                        String image = child.child("image").getValue(String.class);
//                        String name = child.child("title").getValue(String.class);
//                        MyCategory tempCategory = new MyCategory();
//                        tempCategory.setTitle(name);
//                        tempCategory.setImage_cover(image);
//                        myCategories.add(tempCategory);
//
//                    } catch (Exception ex) {}
//                }
//                Log.d("pttt",myCategories.toString());
//                initAdapter();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void initAdapter() {
        categoriesAdapter = new CategoriesAdapter(this, myCategories, new CategoriesAdapter.CategoryListener() {
            @Override
            public void clicked(MyCategory category, int position) {
//                currentCategoryName
                currentCategoryName = category.getTitle();
                Intent intent = new Intent(MainActivity.this, MyAllRecipesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentCategoryName",currentCategoryName);
                intent.putExtra("Bundle",bundle);
                Log.d("pttt",category.toString());
                startActivity(intent);


            }
        });
        main_RECYC_categories.setLayoutManager(new GridLayoutManager(this,2));
        main_RECYC_categories.setAdapter(categoriesAdapter);
    }



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
        header_BAR_progress.setVisibility(View.VISIBLE);
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
                        header_BAR_progress.setVisibility(View.INVISIBLE);
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






}
