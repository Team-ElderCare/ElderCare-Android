package com.example.eldercare.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.eldercare.base.activity.BaseActivity
import com.example.eldercare.databinding.ActivitySplashBinding
import com.example.eldercare.domain.auth.AuthViewModel
import com.example.eldercare.domain.model.UiState
import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// Splash 화면 Activity - 토큰 검증 후 메인/온보딩 분기
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, AuthViewModel>(
    ActivitySplashBinding::inflate,
) {
    override val viewModel by viewModels<AuthViewModel>() // ViewModel 주입

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Android 12 이상 스플래시 스크린
        super.onCreate(savedInstanceState)
        loadSplashScreen() // 스플래시 로직 시작
        collectData() // 데이터 Flow 수집 시작
    }

//    private fun loadSplashScreen() {
//        lifecycleScope.launch {
//            delay(SPLASH_SCREEN_DELAY_TIME) // 1.5초 대기
//            //navigateToMain() // 무조건 메인 화면으로 이동
//        }
//    }

    // 토큰/그룹ID 확인 후 분기처리
    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY_TIME) // 1.5초 딜레이
            if (viewModel.isLocalToken()) { // 로컬 토큰 있는지 체크
                if (viewModel.isLocalGroupId()) {
                    navigateToMain() // 그룹ID 있으면 메인으로
                } else {
                    viewModel.getUserInfo() // 없으면 유저정보 조회
                }
            }
        }
    }

    // 유저 정보 상태 감지
    private fun collectData() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { uiState: UiState<UserInfo> ->
            when (uiState) {
                is UiState.Success -> navigateToMain() // 유저 정보 로딩 후 메인 화면으로 이동
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    // 메인 화면으로 이동
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // 현재 Activity 종료
    }

    companion object {
        const val SPLASH_SCREEN_DELAY_TIME = 1500L // 스플래시 지연시간 1.5초
    }
}
