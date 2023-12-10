package com.example.capstone

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")
class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(context: Context): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(context.dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private object Keys {
        val USER_EMAIL = stringPreferencesKey("token")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    fun getToken(): Flow<String?> = dataStore.data.map { it[Keys.USER_EMAIL] }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_EMAIL] = token
        }
    }

    fun getUserName(): Flow<String?> = dataStore.data.map { it[Keys.USER_NAME] }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_NAME] = userName
        }
    }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.USER_EMAIL)
            preferences.remove(Keys.USER_NAME)
        }
    }
}