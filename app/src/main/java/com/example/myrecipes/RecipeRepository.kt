package com.example.myrecipes

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    val recipes: Flow<List<Recipe>> = recipeDao.getRecipes()
    val favRecipes: Flow<List<Recipe>> = recipeDao.getFavoriteRecipes()

    suspend fun insert(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}