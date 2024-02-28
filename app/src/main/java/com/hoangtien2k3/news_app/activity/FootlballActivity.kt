package com.hoangtien2k3.news_app.activity

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.adapter.NewsFoolballAdapter
import com.hoangtien2k3.news_app.models.Football
import android.widget.ImageView
import com.hoangtien2k3.news_app.api.FootballService
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.viewmodel.NewsFootballViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FootlballActivity : AppCompatActivity() {
    private lateinit var newsRecycler: RecyclerView
    private lateinit var adapter: NewsFoolballAdapter
    private lateinit var model: NewsFootballViewModel
    private lateinit var swipeRefreshLayoutNews: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_footlball)
        swipeRefreshLayoutNews = findViewById(R.id.swipe_layout)
        swipeRefreshLayoutNews.isRefreshing = true
        newsRecycler = findViewById(R.id.NewsRecycler)
        newsRecycler.setHasFixedSize(true)
        newsRecycler.layoutManager = LinearLayoutManager(this)

        model = ViewModelProviders.of(this)[NewsFootballViewModel::class.java]

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_Foolball)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FootballService::class.java)
        val call: Call<List<Football>> = service.getFootballData()

        call.enqueue(object : Callback<List<Football>> {
            override fun onResponse(call: Call<List<Football>>, response: Response<List<Football>>) {
                if (response.isSuccessful) {
                    val footballList = response.body()
                    footballList?.let {
                        adapter = NewsFoolballAdapter(this@FootlballActivity, it, object : NewsFoolballAdapter.ShowDialoginterface {
                            override fun itemClik(hero: Football) {
                                val dialogBuilder = AlertDialog.Builder(this@FootlballActivity)
                                val inflater = layoutInflater
                                val dialogView = inflater.inflate(R.layout.web_show, null)
                                dialogBuilder.setView(dialogView)

                                val mWebView: WebView = dialogView.findViewById(R.id.WebConnect)
                                val button: ImageView = dialogView.findViewById(R.id.ok)

                                val webSettings: WebSettings = mWebView.settings
                                webSettings.javaScriptEnabled = true

                                mWebView.loadData(hero.embed, "text/html", "UTF-8")

                                val alertDialog: AlertDialog = dialogBuilder.create()
                                button.setOnClickListener { alertDialog.dismiss() }
                                alertDialog.show()
                            }
                        })
                        newsRecycler.adapter = adapter
                    }
                }
                swipeRefreshLayoutNews.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Football>>, t: Throwable) {
                swipeRefreshLayoutNews.isRefreshing = false
                t.printStackTrace()
            }
        })

        swipeRefreshLayoutNews.setOnRefreshListener {
            swipeRefreshLayoutNews.isRefreshing = true
            addData()
        }

        addData()
    }

    private fun addData() {

    }
}
