package com.sepidehmiller.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sepidehmiller.bakingapp.data.Recipe;
import com.sepidehmiller.bakingapp.network.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
  public static final String TAG = MainActivity.class.getSimpleName();
  private List<Recipe> mRecipes;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final RecyclerView rv = findViewById(R.id.main_recycler_view);

    /* How do I know if this is a tablet?
    https://android-developers.googleblog.com/2011/07/new-tools-for-managing-screen-sizes.html */

    if (getResources().getConfiguration().smallestScreenWidthDp >= 600) {
      rv.setLayoutManager(new GridLayoutManager(this, 3));
    } else {
      rv.setLayoutManager(new LinearLayoutManager(this));
    }

    Call<List<Recipe>> call = NetworkUtils.buildRecipeListCall();

    call.enqueue(new Callback<List<Recipe>>() {
      @Override
      public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
        if (response.message().contentEquals("OK")) {
          mRecipes = response.body();
          rv.setAdapter(new RecipeAdapter(MainActivity.this, mRecipes));
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        Log.i(TAG, t.toString() );
      }
    });
  }
}
