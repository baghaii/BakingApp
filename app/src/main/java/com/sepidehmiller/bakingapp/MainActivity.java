package com.sepidehmiller.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sepidehmiller.bakingapp.data.Recipe;
import com.sepidehmiller.bakingapp.network.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Call<List<Recipe>> call = NetworkUtils.buildRecipeListCall();

    call.enqueue(new Callback<List<Recipe>>() {
      @Override
      public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
        if (response.message().contentEquals("OK")) {
          for (Recipe recipe : response.body()) {
            Log.i(TAG, recipe.getName());
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        Log.i(TAG, t.toString() );
      }
    });
  }
}
