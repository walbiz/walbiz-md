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

        // try token
//        private val EMAIL_KEY = stringPreferencesKey("email")
//        private val TOKEN_KEY = stringPreferencesKey("token")
//        private val IS_LOGIN_KEY = stringPreferencesKey("islogin")
        // end token

        fun getInstance(context: Context): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(context.dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private object Keys {
        val USER_TOKEN = stringPreferencesKey("token")
        val USER_NAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("email")
    }

    fun getToken(): Flow<String?> = dataStore.data.map { it[Keys.USER_TOKEN] }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_TOKEN] = token
        }
    }

    fun getUserName(): Flow<String?> = dataStore.data.map { it[Keys.USER_NAME] }

    fun getEmail(): Flow<String?> = dataStore.data.map { it[Keys.EMAIL] }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_NAME] = userName
        }
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[Keys.EMAIL] = email
        }
    }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.USER_TOKEN)
            preferences.remove(Keys.USER_NAME)
        }
    }

    // try get session
//    fun getSession(): Flow<UserModel> {
//        return dataStore.data.map { preferences ->
//            UserModel(
//                preferences[EMAIL_KEY] ?: "",
//                preferences[TOKEN_KEY] ?: "",
//            )
//        }
//    }
    // end get session
}