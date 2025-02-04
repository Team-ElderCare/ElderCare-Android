package com.example.eldercare.presentation.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.eldercare.BuildConfig
import com.example.eldercare.base.activity.BaseActivity
import com.example.eldercare.databinding.ActivityAuthBinding
import com.example.eldercare.domain.auth.AuthViewModel
import com.example.eldercare.domain.model.UiState
import com.example.eldercare.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>(
    ActivityAuthBinding::inflate
) {
    override val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKakaoLogin()
        collectKakaoLogin()
    }

    private fun setupKakaoLogin() {
        binding.btnKakaoLogin.setOnClickListener {
            // TODO: 카카오 로그인 구현
            // 임시로 바로 메인으로 이동
            navigateToMain()

            /* 카카오 로그인 구현부분 주석처리
            val kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?client_id=${BuildConfig.KAKAO_API_KEY}&redirect_uri=${BuildConfig.KAKAO_REDIRECT_URI}&response_type=code"

            binding.webView.apply {
                visibility = View.VISIBLE
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url?.startsWith(BuildConfig.KAKAO_REDIRECT_URI) == true) {
                            val code = Uri.parse(url).getQueryParameter("code")
                            code?.let {
                                viewModel.kakaoLogin(it)
                                visibility = View.GONE
                            }
                            return true
                        }
                        return false
                    }
                }
                loadUrl(kakaoAuthUrl)
            }
            */
        }
    }

    private fun collectKakaoLogin() {
        viewModel.kakaoLoginState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> navigateToMain()
                is UiState.Error -> showError(uiState.message)
                is UiState.Loading -> showLoading()
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        // TODO: 로딩 표시 구현
    }
}
