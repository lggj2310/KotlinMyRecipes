package com.example.myrecipes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myrecipes.ui.theme.MyRecipesTheme
import com.google.firebase.auth.FirebaseAuth

fun isUserLoggedIn(): Boolean {
    val user = FirebaseAuth.getInstance().currentUser
    return user != null
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            AppNavigation(navController)
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(navController: NavHostController) {
    val recipeViewModel: RecipeViewModel = viewModel()
    val authViewModel = AuthViewModel()

    CheckUserSession(navController, authViewModel)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, authViewModel)
        }

        composable("recipes") {
            RecipeListScreen(navController, recipeViewModel, authViewModel)
        }

        composable("addRecipe") {
            RecipeAddScreen(navController, onSave = { newRecipe ->
                recipeViewModel.insert(newRecipe)
            })
        }

        composable("recipeDetail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            recipeId?.let { id ->
                val recipe = recipeViewModel.recipes.collectAsState(initial = emptyList()).value.find { it.id == id }
                recipe?.let {
                    RecipeDetailScreen(navController, it, onEdit = {
                        navController.navigate("editRecipe/$id")
                    }, onDelete = {
                        recipeViewModel.delete(it)
                    })
                }
            }
        }

        composable("editRecipe/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            recipeId?.let { id ->
                val recipe = recipeViewModel.recipes.collectAsState(initial = emptyList()).value.find { it.id == id }
                recipe?.let {
                    RecipeEditScreen(navController, it, onSave = { updatedRecipe ->
                        recipeViewModel.update(updatedRecipe)
                    })
                }
            }
        }
    }
}

@Composable
fun CheckUserSession(navController: NavHostController, authViewModel: AuthViewModel) {
    val isLoggedIn = authViewModel.isUserLoggedIn()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("recipes") {
                popUpTo("login") { inclusive = true } // Evita volver a login
            }
        } else {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyRecipesTheme {
        Greeting("Android")
    }
}