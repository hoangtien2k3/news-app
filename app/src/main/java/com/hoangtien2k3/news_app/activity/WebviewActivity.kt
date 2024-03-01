package com.hoangtien2k3.news_app.activity

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.hoangtien2k3.news_app.R

class WebviewActivity : AppCompatActivity() {

    private lateinit var mWebView: WebView
    private lateinit var progressBarWebView: ProgressBar
    private lateinit var oke: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tin_tuc)

        progressBarWebView = findViewById(R.id.progressBarWebView)
        mWebView = findViewById(R.id.activityTinTuc_WebView)
        oke = findViewById(R.id.ok)
        oke.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        intent.getStringExtra("link")?.let { link ->
            mWebView.apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                        progressBarWebView.visibility = View.GONE
                    }
                }
                loadUrl(link)
            }
        }

    }
}
