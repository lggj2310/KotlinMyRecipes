package com.example.myrecipes

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager (private val context: Context) {
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val SESSION_KEY = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences -> preferences[SESSION_KEY] ?: false }

    fun saveUserSession(context: Context, userId: String) {
        runBlocking {
            context.dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = userId
            }
        }
    }

    fun getUserSession(context: Context): String? {
        return runBlocking {
            context.dataStore.data.first()[USER_ID_KEY]
        }
    }

    fun clearSession(context: Context) {
        runBlocking {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }
    suspend fun saveSession(isLoggedIn: Boolean) {
        context.dataStore.edit {
            preferences -> preferences[SESSION_KEY] = isLoggedIn
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences -> preferences[SESSION_KEY] = false }
    }
}