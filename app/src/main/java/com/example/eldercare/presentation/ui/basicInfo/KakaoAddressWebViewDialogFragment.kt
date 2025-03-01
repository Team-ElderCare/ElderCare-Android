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

class KakaoAddressWebViewDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_kakao_address_webview, container, false)
        val webView = view.findViewById<WebView>(R.id.webView)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowUniversalAccessFromFileURLs = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                return if (url.startsWith("file:///android_asset/")) {
                    false
                } else {
                    true
                }
            }
        }


        webView.addJavascriptInterface(WebAppInterface(), "Android")

        webView.loadUrl("file:///android_asset/kakao_postcode.html")

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
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
