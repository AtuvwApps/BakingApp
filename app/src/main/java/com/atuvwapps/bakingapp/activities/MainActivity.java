package com.atuvwapps.bakingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.atuvwapps.bakingapp.adapters.RecipeRecyclerViewAdapter;
import com.atuvwapps.bakingapp.data.Recipe;
import com.atuvwapps.bakingapp.databinding.ActivityMainBinding;
import com.atuvwapps.bakingapp.retrofit.RetrofitInterface;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.ItemClickListener {

    private ActivityMainBinding binding;
    private RecipeRecyclerViewAdapter adapter;
    private List<Recipe> recipes;
    private String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/";
    private String PATH = "2017/May/59121517_baking/baking.json";
    private RetrofitInterface myInterface;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myInterface = retrofit.create(RetrofitInterface.class);
        recipes = new ArrayList<Recipe>();
        getRecipes(PATH);
    }

    public void getRecipes(String path){
        Call<List<Recipe>> call = myInterface.getRecipes(path);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()) {
                    List<Recipe> results = response.body();
                    recipes = results;
                    startRecycler();
                }else{
                    Log.e("Get not successful", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void startRecycler(){
        adapter = new RecipeRecyclerViewAdapter(MainActivity.this, recipes, this);
        binding.recipesRv.setAdapter(adapter);
        binding.recipesRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(int position) {
        openRecipeDetailActivity(recipes.get(position));
    }

    public void openRecipeDetailActivity(Recipe recipe){
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("Recipe", recipe);
        startActivity(intent);
    }
}