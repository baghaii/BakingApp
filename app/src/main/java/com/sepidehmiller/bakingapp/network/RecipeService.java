package com.sepidehmiller.bakingapp.network;

import com.sepidehmiller.bakingapp.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface RecipeService {

  @GET("baking.json")
  Call<List<Recipe>> getRecipes();

}
