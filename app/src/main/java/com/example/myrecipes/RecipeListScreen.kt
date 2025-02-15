package com.example.myrecipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.util.TableInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(navController: NavController, recipes: List<Recipe>) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Recetas") }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(recipes) { recipe ->
                RecipeItem(recipe) {
                    navController.navigate("recipeDetail/${recipe.id}")
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Time: ${recipe.prepTime} min")
            Text(text = if (recipe.isFav) "★ Favorite" else "☆")
        }
    }
}