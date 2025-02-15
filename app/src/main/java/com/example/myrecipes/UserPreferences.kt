package com.example.myrecipes

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences (private val context: Context){
    private val SESSION_KEY = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs -> prefs[SESSION_KEY] ?: false }

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { it[SESSION_KEY] = value }
    }
}