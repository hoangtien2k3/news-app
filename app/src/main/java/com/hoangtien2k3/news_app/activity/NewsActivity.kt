package com.hoangtien2k3.news_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.db.ArticleDatabase
import com.hoangtien2k3.news_app.repository.NewsRepository
import com.hoangtien2k3.news_app.viewmodel.NewsViewModel
import com.hoangtien2k3.news_app.viewmodel.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {

//    lateinit var viewModel: NewsViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_news)
//
//        val newsRepository = NewsRepository(ArticleDatabase(this))
//        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
//
//        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
//
//       // bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
//
//        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
//        val navController= navHostFragment.navController
//        bottomNavigationView.setupWithNavController(navController)
//
//    }
}