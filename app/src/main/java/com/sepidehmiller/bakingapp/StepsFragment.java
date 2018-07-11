package com.sepidehmiller.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sepidehmiller.bakingapp.data.Ingredient;

import java.util.ArrayList;

public class StepsFragment extends Fragment {
  TextView mIngredientsTextView;

  public StepsFragment() {

  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
    mIngredientsTextView = rootView.findViewById(R.id.ingredients_text_view);

    /* How do I pass data to Fragments?
    http://www.androhub.com/android-pass-data-from-activity-to-fragment/ */

    ArrayList<Ingredient> ingredients = getArguments().getParcelableArrayList(RecipeAdapter.INGREDIENTS);

    if (ingredients != null && ingredients.size() > 0) {

        StringBuilder builder = new StringBuilder();


        for (Ingredient ingredient : ingredients) {
          Double quantity = ingredient.getQuantity();

          //Only include units behind the decimal if you need them.
          if (quantity != Math.round(quantity)) {
            builder.append(quantity.toString());
          } else {
            builder.append(Math.round(quantity));
          }

          builder.append(" ");

          //UNIT is usually just used for eggs and does not need to specified.
          //Ex. 3 eggs instead of 3 UNIT eggs.
          if (!ingredient.getMeasure().contentEquals("UNIT")) {
            builder.append(ingredient.getMeasure());
          }

          builder.append(" ");
          builder.append(ingredient.getIngredient());
          builder.append("\n");
        }

        String ingredientString = builder.toString().trim();
        mIngredientsTextView.setText(ingredientString);
    }

    return rootView;

  }

}
