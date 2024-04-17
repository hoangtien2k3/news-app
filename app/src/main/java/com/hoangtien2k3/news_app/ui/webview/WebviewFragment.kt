package com.hoangtien2k3.news_app.ui.webview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.FragmentWebviewBinding
import com.hoangtien2k3.news_app.utils.viewBinding

class WebviewFragment : Fragment(R.layout.fragment_webview) {
    private val binding by viewBinding(FragmentWebviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.back.setOnClickListener { requireActivity().onBackPressed() }
        val link = arguments?.getString("link") ?: arguments?.getString("linknews")
        link?.let {
            loadUrlToWebView(it)
            Log.d("link1", it)
        }
    }

    private fun loadUrlToWebView(link: String) {
        binding.WebConnect.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    binding.progressBarWebView.visibility = View.GONE
                }
            }
            loadUrl(link)
        }
    }

}
