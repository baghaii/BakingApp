package com.sepidehmiller.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sepidehmiller.bakingapp.data.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

  private List<Recipe> mRecipes;

  public RecipeAdapter(List<Recipe> recipes) {
    mRecipes = recipes;
  }

  @NonNull
  @Override
  public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.item_main, parent, false);

    RecyclerViewClickListener listener = new RecyclerViewClickListener() {
      @Override
      public void onClick(View view, int i) {
        //TODO - Bring up recipe activity.
      }
    };
    return new RecipeHolder(view, listener);
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
    Recipe recipe = mRecipes.get(position);
    holder.setRecipe(recipe.getName(), recipe.getServings().toString() + " servings");
  }

  @Override
  public int getItemCount() {
    return mRecipes.size();
  }

  public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewClickListener mRecyclerViewClickListener;
    private TextView mRecipeTextView;
    private TextView mServingTextView;

    public RecipeHolder(View itemView, RecyclerViewClickListener listener) {
      super(itemView);
      mRecyclerViewClickListener = listener;
      mRecipeTextView = itemView.findViewById(R.id.recipe_text_view);
      mServingTextView = itemView.findViewById(R.id.serving_text_view);
    }

    @Override
    public void onClick(View v) {
      mRecyclerViewClickListener.onClick(v, getAdapterPosition());
    }

    public void setRecipe(String recipe, String serving) {
      mRecipeTextView.setText(recipe);
      mServingTextView.setText(serving);
    }
  }

}
