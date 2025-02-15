package com.example.myrecipes.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipes.recipes.data.Recipe
import com.example.myrecipes.recipes.data.RecipeDatabase
import com.example.myrecipes.recipes.data.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RecipeViewModel(app: Application) : AndroidViewModel(app) {
    private val database = RecipeDatabase.getDatabase(app)
    private val repository = RecipeRepository(database.recipeDao())

    val recipes: Flow<List<Recipe>> = repository.recipes
    val favRecipes: Flow<List<Recipe>> = repository.favRecipes

    fun insert(recipe: Recipe) {
        viewModelScope.launch {
            repository.insert(recipe)
        }
    }

    fun update(recipe: Recipe) {
        viewModelScope.launch { repository.update(recipe) }
    }

    fun delete(recipe: Recipe) {
        viewModelScope.launch {
            repository.delete(recipe)
        }
    }
}