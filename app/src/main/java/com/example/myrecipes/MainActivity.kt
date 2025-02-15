package com.example.myrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myrecipes.ui.theme.MyRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val recipeViewModel: RecipeViewModel = viewModel()
    val recipes = listOf(
        Recipe(1,"Pasta Carbonara", "", 20, false),
        Recipe(2,"Ensalada César", "",15, true),
        Recipe(3,"Sopa de Tomate", "", 30, false)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }

        composable("recipes") {
            RecipeListScreen(navController, recipes)
        }

//        composable("recipeDetail/{recipeId}") { backStackEntry ->
//            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
//            recipeId?.let {
//                RecipeDetailScreen(recipeId = it, viewModel = recipeViewModel)
//            }
//        }
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