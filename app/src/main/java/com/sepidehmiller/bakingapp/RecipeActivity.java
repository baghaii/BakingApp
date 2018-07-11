package com.sepidehmiller.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sepidehmiller.bakingapp.data.Ingredient;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

  ArrayList<Ingredient> mIngredients;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);

    StepsFragment stepsFragment = new StepsFragment();

    Bundle data = getIntent().getExtras();

    FragmentManager fm = getSupportFragmentManager();
    fm.beginTransaction()
        .add(R.id.step_list_container, stepsFragment)
        .commit();

    if (data != null && data.containsKey(RecipeAdapter.INGREDIENTS)) {
     stepsFragment.setArguments(data);
    }
  }


}
