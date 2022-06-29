package com.example.foodelicious.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodelicious.Adapters.IngredientAdapter;
import com.example.foodelicious.Firebase.MyDataManager;
import com.example.foodelicious.Objects.Ingredient;
import com.example.foodelicious.Objects.MyCategory;
import com.example.foodelicious.Objects.MyRecipe;
import com.example.foodelicious.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_create_recipe extends AppCompatActivity {

    private TextInputLayout form_EDT_name;
    private EditText editItem_EDT_notes;
    private TextInputLayout createRecipe_TIN_category;
    private RecyclerView ingredients_RECYC;
    private FloatingActionButton panel_BTN_add;
    private MaterialButton panel_BTN_create;
    private AutoCompleteTextView category_AutoCompleteTextViewCategory;
    private String categorySelected;
    private ArrayAdapter<String> categoryAdapter;
    private ArrayList<Ingredient> ingredients;
    private IngredientAdapter ingredientAdapter;

    private final MyDataManager dataManager = MyDataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();


    public static final String KEY_RECIPES = "recipes";
    public static final String KEY_USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        dataManager.setMyRecipesPath("create");
        findViews();
        initAdapter();
        initButtons();
    }


    private void initAdapter() {
        ingredientAdapter = new IngredientAdapter(this,ingredients);
        ingredients_RECYC.setLayoutManager(new GridLayoutManager(this,1));
        ingredients_RECYC.setAdapter(ingredientAdapter);



        ingredientAdapter.setIngredientListener(new IngredientAdapter.IngredientListener() {
            @Override
            public void clicked(Ingredient ingredient, int position,String name) {
                ingredient.setName(name);
                Toast.makeText(Activity_create_recipe.this, ingredient.getName(),Toast.LENGTH_SHORT).show();
                ingredients_RECYC.getAdapter().notifyItemChanged(position);
            }


            @Override
            public void plus(Ingredient ingredient, int position) {
                ingredient.addAmount(1);
                ingredients_RECYC.getAdapter().notifyItemChanged(position);
            }
            @Override
            public void minus(Ingredient ingredient, int position) {
                if(ingredient.getAmount()==0){
                    Toast.makeText(Activity_create_recipe.this,"Amount Cannot Be Negative",Toast.LENGTH_SHORT).show();
                }else{
                    ingredient.addAmount(-1);
                }
                ingredients_RECYC.getAdapter().notifyItemChanged(position);
            }

        });

        ingredientAdapter.notifyDataSetChanged();

        categoryAdapter = new ArrayAdapter<String>(this,R.layout.drop_down_category,dataManager.getCategoriesName());
        category_AutoCompleteTextViewCategory.setAdapter(categoryAdapter);
    }

    private void findViews() {
        Ingredient ingredient = new Ingredient();
        ingredients = new ArrayList<Ingredient>();
        ingredients.add(ingredient);
        form_EDT_name = findViewById(R.id.form_EDT_name);
        editItem_EDT_notes = findViewById(R.id.editItem_EDT_notes);
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
        ingredients_RECYC = findViewById(R.id.ingredients_RECYC);
        panel_BTN_add = findViewById(R.id.panel_BTN_add);
        createRecipe_TIN_category = findViewById(R.id.createRecipe_TIN_category);
        category_AutoCompleteTextViewCategory = findViewById(R.id.category_AutoCompleteTextViewCategory);
    }



    private void initButtons() {
        category_AutoCompleteTextViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categorySelected = adapterView.getItemAtPosition(i).toString();
            }
        });

        panel_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient ingredient = new Ingredient();
                ingredients.add(ingredient);
                ingredientAdapter.notifyDataSetChanged();
            }
        });

        panel_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecipe tempRecipe = new MyRecipe();
                tempRecipe.setName(form_EDT_name.getEditText().getText().toString());
                tempRecipe.setMethodSteps(editItem_EDT_notes.getText().toString());
                tempRecipe.setCategory(categorySelected);
                getCategory();
                tempRecipe.setFavorite(false);
                tempRecipe.setIngredients(ingredients);
                tempRecipe.setRecipeUid(dataManager.getMyRecipes().size()+1);
                dataManager.getMyRecipes().add(tempRecipe);
                dataManager.addNewRecipe(tempRecipe);
                dataManager.getCallBackCreateRecipe().createRecipe();
                finish();
            }
        });


    }

    private void getCategory() {
        for(int i=0;i<dataManager.getMyCategories().size();i++){
            if(dataManager.getMyCategories().get(i).getTitle().equals(categorySelected)){
                dataManager.getMyCategories().get(i).setItems_Counter(dataManager.getMyCategories().get(i).getItems_Counter()+1);
            }
        }
    }


}