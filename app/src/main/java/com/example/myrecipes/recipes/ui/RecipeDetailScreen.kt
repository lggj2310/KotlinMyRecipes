package com.example.myrecipes.recipes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myrecipes.recipes.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(navController: NavController, recipe: Recipe, onEdit: () -> Unit,
                       onDelete: () -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false},
            title = { Text("Delete Confirmation") },
            text = { Text("Are you sure that you want to delete this recipe?") },
            confirmButton = {
                Button(onClick = {
                    onDelete()
                    showDeleteDialog = false
                    navController.popBackStack()
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)) {
            Text(text = recipe.desc, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Preparation Time: ${recipe.prepTime} min", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = if (recipe.isFav) "★ It is one of your favorite" else "☆ It is not a favorite", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { navController.navigate("editRecipe/${recipe.id}") }, modifier = Modifier.weight(1f)) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {showDeleteDialog = true}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                    Text("Delete")
                }
            }
        }
    }
}