package com.example.myrecipes

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager (private val context: Context) {
    private val SESSION_KEY = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences -> preferences[SESSION_KEY] ?: false }

    suspend fun saveSession(isLoggedIn: Boolean) {
        context.dataStore.edit {
            preferences -> preferences[SESSION_KEY] = isLoggedIn
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences -> preferences[SESSION_KEY] = false }
    }
}