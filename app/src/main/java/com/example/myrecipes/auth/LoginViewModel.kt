package com.example.myrecipes.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipes.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val sessionManager = SessionManager(app)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            sessionManager.isLoggedIn.collect {
                loggedIn -> _isLoggedIn.value = loggedIn
            }
        }
    }

    fun login(email: String, pass: String) {
        if (email == "info@koalit.dev" && pass == "koalit123") {
            viewModelScope.launch {
                sessionManager.saveSession(true)
            }
        } else {
            Toast.makeText(null, "Incorrect Credentials", Toast.LENGTH_SHORT).show()
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
        }
    }
}