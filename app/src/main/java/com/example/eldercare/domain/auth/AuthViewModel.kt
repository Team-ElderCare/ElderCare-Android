package com.example.eldercare.domain.auth

import androidx.lifecycle.viewModelScope
import com.example.eldercare.base.viewmodel.BaseViewModel
import com.example.eldercare.domain.model.UiState
import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.domain.repository.AuthRepository
import com.example.eldercare.domain.usecase.PostSignInUseCase
import com.example.eldercare.util.token.AccessTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class AuthViewModel
//    @Inject
//    constructor(
//        private val postSignInUseCase: PostSignInUseCase,
//        private val accessTokenManager: AccessTokenManager,
//    ) : BaseViewModel() {
//        private val _userInfoState = MutableStateFlow<UiState<UserInfo>>(UiState.Loading)
//        val userInfoState = _userInfoState.asStateFlow()
//
//        fun isLocalToken() = accessTokenManager.hasToken()
//
//        fun isLocalGroupId() = accessTokenManager.hasGroupId()
//
//        fun getUserInfo() =
//            viewModelScope.launch {
//                postSignInUseCase()
//                    .onSuccess { _userInfoState.value = UiState.Success(it) }
//                    .onFailure { _userInfoState.value = UiState.Error(it.message ?: "") }
//            }
//    }


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _userInfoState = MutableStateFlow<UiState<UserInfo>>(UiState.Empty)
    val userInfoState = _userInfoState.asStateFlow()

    private val _kakaoLoginState = MutableStateFlow<UiState<UserInfo>>(UiState.Empty)
    val kakaoLoginState = _kakaoLoginState.asStateFlow()

    suspend fun isLocalToken() = authRepository.isLocalToken()
    suspend fun isLocalGroupId() = authRepository.isLocalGroupId()

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfoState.value = UiState.Loading
            authRepository.getUserInfo()
                .onSuccess { userInfo ->
                    _userInfoState.value = UiState.Success(userInfo)
                }
                .onFailure { exception ->
                    _userInfoState.value = UiState.Error(exception.message ?: "Error")
                }
        }
    }

    fun kakaoLogin(code: String) {
        viewModelScope.launch {
            _kakaoLoginState.value = UiState.Loading
            authRepository.kakaoLogin(code)
                .onSuccess { userInfo ->
                    _kakaoLoginState.value = UiState.Success(userInfo)
                }
                .onFailure { exception ->
                    _kakaoLoginState.value = UiState.Error(exception.message ?: "Error")
                }
        }
    }
}
