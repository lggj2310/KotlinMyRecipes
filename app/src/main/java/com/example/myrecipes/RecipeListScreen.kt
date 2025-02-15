package com.example.myrecipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(navController: NavController, viewModel: RecipeViewModel, authViewModel: AuthViewModel) {
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())
    var showOnlyFavorites by remember { mutableStateOf(false) }
    var sortByTime by remember { mutableStateOf(false) }

    val filteredRecipes = recipes
        .filter { !showOnlyFavorites || it.isFav }
        .let { if (sortByTime) it.sortedBy { recipe -> recipe.prepTime } else it }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recetas") }, actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login") {
                    popUpTo("recipes") { inclusive = true }
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
            } }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addRecipe") }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Favorite Recipes")
                Switch(
                    checked = showOnlyFavorites,
                    onCheckedChange = { showOnlyFavorites = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Order By Preparation Time")
                Switch(
                    checked = sortByTime,
                    onCheckedChange = { sortByTime = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(filteredRecipes) { recipe ->
                    RecipeItem(recipe) {
                        navController.navigate("recipeDetail/${recipe.id}")
                    }
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
        onClick = { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Time: ${recipe.prepTime} min")
            Text(text = if (recipe.isFav) "★ Favorite" else "☆")
        }
    }
}