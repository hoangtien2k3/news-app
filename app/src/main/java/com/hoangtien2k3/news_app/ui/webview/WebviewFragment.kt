package com.hoangtien2k3.news_app.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.hoangtien2k3.news_app.databinding.FragmentWebviewBinding

class WebviewFragment : Fragment() {
    private var _binding: FragmentWebviewBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val link = arguments?.getString("link") ?: arguments?.getString("linknews")
        link?.let { loadUrlToWebView(it) }
    }

    private fun loadUrlToWebView(link: String) {
        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    binding.progressBarWebView.visibility = View.GONE
                }
            }
            loadUrl(link)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
