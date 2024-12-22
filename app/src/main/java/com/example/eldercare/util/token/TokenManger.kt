package com.example.eldercare.util.token

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveToken(token: String) = runBlocking {
        context.tokenDataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
        Timber.tag("TokenManager").d("토큰 : $token")
    }

    fun getAccessToken(): Flow<String?> {
        return  context.tokenDataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }
    }

    companion object {
        private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "info")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    }
}
