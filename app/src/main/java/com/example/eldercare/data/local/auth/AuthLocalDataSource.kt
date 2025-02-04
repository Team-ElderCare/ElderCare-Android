package com.example.eldercare.data.local.auth

import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_GROUP_ID = "group_id"
    }

    suspend fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    suspend fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    suspend fun saveGroupId(groupId: String) {
        prefs.edit().putString(KEY_GROUP_ID, groupId).apply()
    }

    suspend fun getGroupId(): String? {
        return prefs.getString(KEY_GROUP_ID, null)
    }
}
