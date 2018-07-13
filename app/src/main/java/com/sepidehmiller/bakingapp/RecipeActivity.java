package com.sepidehmiller.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RecipeActivity extends AppCompatActivity {

  StepsFragment stepsFragment;
  PlayerFragment playerFragment;
  View playerContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);

    // Why was my Fragment drawing on top of an existing Fragment on rotation?
    // https://stackoverflow.com/questions/5164126/retain-the-fragment-object-while-rotating

   if (savedInstanceState == null) {

      stepsFragment = new StepsFragment();
      playerFragment = new PlayerFragment();

      Bundle data = getIntent().getExtras();

      if (data.containsKey(RecipeAdapter.RECIPE)) {
        setTitle(data.getString(RecipeAdapter.RECIPE));
      }

      FragmentManager fm = getSupportFragmentManager();
      fm.beginTransaction()
          .add(R.id.step_list_container, stepsFragment)
          .commit();

      playerContainer = findViewById(R.id.exo_player_container);
      if (playerContainer != null) {
        fm.beginTransaction()
            .add(R.id.exo_player_container, playerFragment)
            .commit();
      }

     if (data.containsKey(RecipeAdapter.INGREDIENTS) &&
         (data.containsKey(RecipeAdapter.STEPS))) {

       stepsFragment.setArguments(data);
       playerFragment.setArguments(data);
     }
    }
  }
}
