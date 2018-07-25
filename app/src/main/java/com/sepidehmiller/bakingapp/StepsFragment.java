package com.sepidehmiller.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sepidehmiller.bakingapp.data.Ingredient;
import com.sepidehmiller.bakingapp.data.Step;

import java.util.ArrayList;

public class StepsFragment extends Fragment {
  private TextView mIngredientsTextView;
  private TextView mIngredientsLabelTextView;

  private RecyclerView mStepsRecyclerView;
  private TextView mStepsLabelTextView;

  public RecyclerViewClickListener mListener;

  public StepsFragment() {

  }

  // How do I know if a class has implemented a needed interface?
  // https://developer.android.com/training/basics/fragments/communicating

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      mListener = (RecyclerViewClickListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()
          + " must implement OnRecyclerViewClickListener");
    }

  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
    mIngredientsTextView = rootView.findViewById(R.id.ingredients_text_view);
    mIngredientsLabelTextView = rootView.findViewById(R.id.ingredients_label_text_view);

    /* How do I pass data to Fragments?
    http://www.androhub.com/android-pass-data-from-activity-to-fragment/ */

    if (getArguments() != null && getArguments().containsKey(RecipeAdapter.INGREDIENTS)) {
      ArrayList<Ingredient> ingredients = getArguments().getParcelableArrayList(RecipeAdapter.INGREDIENTS);
      setIngredientsView(ingredients);
    }

    mStepsLabelTextView = rootView.findViewById(R.id.steps_label_text_view);
    mStepsRecyclerView = rootView.findViewById(R.id.steps_recycler_view);

    if (getArguments() != null && getArguments().containsKey(RecipeAdapter.STEPS)) {
      ArrayList<Step> steps = getArguments().getParcelableArrayList(RecipeAdapter.STEPS);
      setStepsView(container.getContext(), steps);
    }

    return rootView;

  }

  private void setIngredientsView(ArrayList<Ingredient> ingredients) {
    if (ingredients != null && ingredients.size() > 0) {

      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < ingredients.size(); i++) {
        builder.append(ingredients.get(i).toString());

        if (i < ingredients.size() - 1) {
          builder.append("\n");
        }
      }
      String ingredientString = builder.toString();
      mIngredientsTextView.setText(ingredientString);
    } else {
      mIngredientsLabelTextView.setVisibility(View.GONE);
    }
  }

  private void setStepsView(Context context, ArrayList<Step> steps) {
   if (steps != null && steps.size() > 0) {
      mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
      mStepsRecyclerView.setAdapter(new StepAdapter(steps, mListener));

    } else {
      mStepsLabelTextView.setVisibility(View.GONE);
    }
  }
}
