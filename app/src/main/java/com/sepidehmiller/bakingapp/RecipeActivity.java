package com.sepidehmiller.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sepidehmiller.bakingapp.data.Step;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements RecyclerViewClickListener {

  public static final String STEP = "Step";
  public static final String STEP_BUNDLE = "StepBundle";
  private StepsFragment stepsFragment;
  private PlayerFragment playerFragment;
  private View playerContainer;
  private ArrayList<Step> mSteps;

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
       mSteps = data.getParcelableArrayList(RecipeAdapter.STEPS);
       Bundle bundle = new Bundle();
       bundle.putParcelable(STEP, mSteps.get(0));

       playerFragment.setArguments(bundle);
     }
    } else {
     // mSteps gets lost on rotation so we had to save it and restore it.
     mSteps = savedInstanceState.getParcelableArrayList(RecipeAdapter.STEPS);
    }
  }

  @Override
  public void onClick(View view, int i) {

    Toast.makeText(this, String.valueOf(i), Toast.LENGTH_LONG).show();
    Bundle bundle = new Bundle();
    bundle.putParcelable(STEP, mSteps.get(i));

    // Launch two-pane mode if you have a container for your second pane.
    if (playerContainer != null) {
      PlayerFragment newPlayerFragment = new PlayerFragment();
      newPlayerFragment.setArguments(bundle);

      FragmentManager fm = getSupportFragmentManager();
      fm.beginTransaction()
          .replace(R.id.exo_player_container, newPlayerFragment)
          .commit();
    } else {
      Intent intent = new Intent(this, RecipeDetailActivity.class);
      intent.putExtra(RecipeAdapter.RECIPE, getTitle());
      intent.putExtra(STEP_BUNDLE, bundle);
      getApplicationContext().startActivity(intent);
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(RecipeAdapter.STEPS, mSteps);
    super.onSaveInstanceState(outState);
  }
}
