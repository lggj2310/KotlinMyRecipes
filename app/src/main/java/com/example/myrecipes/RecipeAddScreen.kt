package com.example.myrecipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RecipeAddScreen(navController: NavController, onSave: (Recipe) -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it},
            label = { Text("Recipe Title") }
        )
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
            label = { Text("Preparation Time (min)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val newRecipe = Recipe(
                title = title,
                desc = desc,
                prepTime = prepTime.toIntOrNull() ?: 0,
                isFav = false
            )
            onSave(newRecipe)
            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}