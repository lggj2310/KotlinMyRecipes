package com.example.myrecipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAddScreen(navController: NavController, onSave: (Recipe) -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var isFav by remember { mutableStateOf(false) }
    var isTitleValid by remember { mutableStateOf(true) }
    var isTimeValid by remember { mutableStateOf(true) }

    fun validateInputs(): Boolean {
        isTitleValid = title.isNotEmpty()
        isTimeValid = prepTime.toIntOrNull() != null && prepTime.toInt() > 0
        return isTitleValid && isTimeValid
    }

    Scaffold(
        topBar = {
        TopAppBar(
            title = { Text("New Recipe") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            TextField(
                value = title,
                onValueChange = { title = it},
                label = { Text("Recipe Title") },
                isError = !isTitleValid
            )
            if (!isTitleValid) Text("Title cannot be empty", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = desc,
                onValueChange = { desc = it},
                label = { Text("Recipe Description") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = prepTime,
                onValueChange = { prepTime = it },
                label = { Text("Preparation Time (min)") },
                isError = !isTimeValid
            )
            if (!isTimeValid) Text("It must be more than 0", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isFav, onCheckedChange = { isFav = it })
                Text("Favorite")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (validateInputs()) {
                    val newRecipe = Recipe(
                        title = title,
                        desc = desc,
                        prepTime = prepTime.toIntOrNull() ?: 0,
                        isFav = false
                    )
                    onSave(newRecipe)
                    navController.popBackStack()
                }
            }) {
                Text("Save")
            }
        }
    }

}