package com.example.myrecipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo

@Composable
fun RecipeListScreen() {
    val recipes = remember { mutableStateListOf("Pizza", "Pasta", "Hamburguesa") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(recipes) { recipe ->
                Text(text = recipe, modifier = Modifier.padding(16.dp))
            }
        }
    }
}