package com.example.myrecipes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel ) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(true) }
    var isValidPassword by remember { mutableStateOf(true) }

    fun validateInputs(): Boolean {
        isValidEmail = email.contains("@") && email.contains(".")
        isValidPassword = pass.length >= 6
        return isValidEmail && isValidPassword
    }

    LaunchedEffect(Unit) {
        if (FirebaseAuth.getInstance().currentUser != null) {
            navController.navigate("recipes") {
                popUpTo("login") { inclusive = true } // Evita volver a la pantalla de login
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isValidEmail
        )

        if (!isValidEmail) Text("Invalid email", color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = !isValidPassword
        )
        if (!isValidPassword) Text("Min. characters: 6", color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick =  {
            if (validateInputs()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("recipes")
                        } else {
                            errorMessage = task.exception?.localizedMessage ?: "Login Error"
                        }
                    }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Log In")
        }

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}