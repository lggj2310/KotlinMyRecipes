package com.example.myrecipes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController ) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val validEmail = "info@koalit.dev"
    val validPassword = "koalit123"
//    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick =  {
            if (email == validEmail && pass == validPassword) {
                navController.navigate("recipes")
            } else {
                Toast.makeText(null, "Incorrect Credentials", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Log In")
        }
    }
}