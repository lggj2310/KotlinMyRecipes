package com.example.myrecipes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun getRecipes(): kotlinx.coroutines.flow.Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isFav = 1")
    fun getFavoriteRecipes(): kotlinx.coroutines.flow.Flow<List<Recipe>>

    @Query("SELECT * FROM recipes ORDER BY prepTime ASC")
    fun getRecipesByTime(): kotlinx.coroutines.flow.Flow<List<Recipe>>

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}