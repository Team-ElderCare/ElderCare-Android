package com.example.eldercare.data.local.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthLocalDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        // 암호화에 사용될 마스터 키 생성
        private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // EncryptedSharedPreferences 인스턴스 생성
        private val prefs: SharedPreferences =
            EncryptedSharedPreferences.create(
                // 암호화된 SharedPreferences의 파일명
                "secure_prefs",
                masterKeyAlias,
                context,
                // 키 암호화 방식
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                // 값 암호화 방식
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )

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
