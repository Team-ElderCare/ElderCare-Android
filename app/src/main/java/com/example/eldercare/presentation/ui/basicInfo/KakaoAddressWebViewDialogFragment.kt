package com.example.eldercare.presentation.ui.basicInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.example.eldercare.R
import com.example.eldercare.databinding.FragmentKakaoAddressWebviewBinding

class KakaoAddressWebViewDialogFragment : DialogFragment() {
    private var _binding: FragmentKakaoAddressWebviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentKakaoAddressWebviewBinding.inflate(inflater, container, false)
        setupWebView()
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() =
        with(binding.webView) {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowUniversalAccessFromFileURLs = true
            }
            webViewClient =
                object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        val url = request?.url.toString()
                        return !url.startsWith("file:///android_asset/")
                    }
                }
            addJavascriptInterface(WebAppInterface(), "Android")
            loadUrl("file:///android_asset/kakao_postcode.html")
        }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun processAddress(addressJson: String) {
            Handler(Looper.getMainLooper()).post {
                val bundle = Bundle().apply { putString("selectedAddress", addressJson) }
                parentFragmentManager.setFragmentResult("addressResult", bundle)
                dismiss()
            }
        }
    }
}
