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
import com.example.eldercare.presentation.ui.login.LoginActivity
import com.example.eldercare.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, AuthViewModel>(
    ActivitySplashBinding::inflate,
) {
    override val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
        collectData()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY_TIME)
            if (viewModel.isLocalToken()) {
                if (viewModel.isLocalGroupId()) {
                    navigateToMain()
                } else {
                    viewModel.getUserInfo()
                }
            } else {
                navigateToLogin() // 토큰이 없으면 로그인 화면으로 이동
            }
        }
    }

    private fun collectData() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> navigateToMain()
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    companion object {
        const val SPLASH_SCREEN_DELAY_TIME = 1500L
    }
}
