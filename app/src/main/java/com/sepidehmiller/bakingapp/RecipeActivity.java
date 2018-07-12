package com.sepidehmiller.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class RecipeActivity extends AppCompatActivity {

  StepsFragment stepsFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);

    stepsFragment = new StepsFragment();

    Bundle data = getIntent().getExtras();

    if (data.containsKey(RecipeAdapter.RECIPE)) {
      setTitle(data.getString(RecipeAdapter.RECIPE));
    }

    FragmentManager fm = getSupportFragmentManager();
    fm.beginTransaction()
        .add(R.id.step_list_container, stepsFragment)
        .commit();

    if ( data.containsKey(RecipeAdapter.INGREDIENTS) ||
        (data.containsKey(RecipeAdapter.STEPS))) {

        stepsFragment.setArguments(data);
    }

  }
}
