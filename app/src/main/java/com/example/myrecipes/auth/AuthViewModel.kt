package com.example.myrecipes.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    fun logout(onLogout: () -> Unit) {
        auth.signOut()
        onLogout()
    }
}