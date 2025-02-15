package com.example.myrecipes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val desc: String,
    val prepTime: Int,
    val isFav: Boolean = false,
    val imagePath: String? = null
)