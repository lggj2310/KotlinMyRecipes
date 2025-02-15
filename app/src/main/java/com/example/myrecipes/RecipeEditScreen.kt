package com.example.myrecipes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEditScreen(navController: NavController, recipe: Recipe, onSave: (Recipe) -> Unit) {
    var title by remember { mutableStateOf(recipe.title) }
    var desc by remember { mutableStateOf(recipe.desc) }
    var time by remember { mutableStateOf(recipe.prepTime.toString()) }
    var isFavorite by remember { mutableStateOf(recipe.isFav) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Recipe") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Preparation Time") })
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isFavorite, onCheckedChange = { isFavorite = it })
                Text("Favorite")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onSave(recipe.copy(title = title, desc = desc, prepTime = time.toIntOrNull() ?: 0, isFav = isFavorite))
                navController.popBackStack()
            }) {
                Text("Save")
            }
        }
    }
}