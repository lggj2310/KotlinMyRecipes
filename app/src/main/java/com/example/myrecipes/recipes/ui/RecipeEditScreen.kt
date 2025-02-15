package com.example.myrecipes.recipes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myrecipes.recipes.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEditScreen(navController: NavController, recipe: Recipe, onSave: (Recipe) -> Unit) {
    var title by remember { mutableStateOf(recipe.title) }
    var desc by remember { mutableStateOf(recipe.desc) }
    var time by remember { mutableStateOf(recipe.prepTime.toString()) }
    var isFavorite by remember { mutableStateOf(recipe.isFav) }
    var isTitleValid by remember { mutableStateOf(true) }
    var isTimeValid by remember { mutableStateOf(true) }

    fun validateInputs(): Boolean {
        isTitleValid = title.isNotEmpty()
        isTimeValid = time.toIntOrNull() != null && time.toInt() > 0
        return isTitleValid && isTimeValid
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Recipe") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                isError = !isTitleValid
            )
            if (!isTitleValid) Text("Title cannot be empty", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Description") }
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Preparation Time") },
                isError = !isTimeValid
            )
            if (!isTimeValid) Text("It must be more than 0", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isFavorite, onCheckedChange = { isFavorite = it })
                Text("Favorite")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (validateInputs()) {
                    onSave(
                        recipe.copy(
                            title = title,
                            desc = desc,
                            prepTime = time.toIntOrNull() ?: 0,
                            isFav = isFavorite
                        )
                    )
                    navController.popBackStack()
                }
            }) {
                Text("Save")
            }
        }
    }
}