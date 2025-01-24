package com.example.eldercare.domain.auth

import androidx.lifecycle.viewModelScope
import com.example.eldercare.base.viewmodel.BaseViewModel
import com.example.eldercare.domain.model.UiState
import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.domain.usecase.PostSignInUseCase
import com.example.eldercare.util.token.AccessTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val postSignInUseCase: PostSignInUseCase,
        private val accessTokenManager: AccessTokenManager,
    ) : BaseViewModel() {
        private val _userInfoState = MutableStateFlow<UiState<UserInfo>>(UiState.Loading)
        val userInfoState = _userInfoState.asStateFlow()

        fun isLocalToken() = accessTokenManager.hasToken()

        fun isLocalGroupId() = accessTokenManager.hasGroupId()

        fun getUserInfo() =
            viewModelScope.launch {
                postSignInUseCase()
                    .onSuccess { _userInfoState.value = UiState.Success(it) }
                    .onFailure { _userInfoState.value = UiState.Error(it.message ?: "") }
            }
    }
