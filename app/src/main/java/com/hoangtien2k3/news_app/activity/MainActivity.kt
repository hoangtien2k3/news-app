package com.hoangtien2k3.news_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.db.ArticleDatabase
import com.hoangtien2k3.news_app.fragment.NewsFragment
import com.hoangtien2k3.news_app.fragment.SearchNewsFragment
import com.hoangtien2k3.news_app.repository.NewsRepository
import com.hoangtien2k3.news_app.viewmodel.NewsViewModel
import com.hoangtien2k3.news_app.viewmodel.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var buttomNavigationView: BottomNavigationView
    private lateinit var fragment_news: Fragment
    private lateinit var fragment_search: Fragment
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
        clickButtonNavigation()


        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]


        // bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
//
//        val navHostFragment= supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
//        val navController= navHostFragment.navController
//        buttomNavigationView.setupWithNavController(navController)

    }

    private fun initializeUI() {
        buttomNavigationView = findViewById(R.id.navMenu)
        fragment_news = NewsFragment()
        fragment_search = SearchNewsFragment()
    }

    private fun clickButtonNavigation() {

        buttomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mnTrangChu -> loadFragment(fragment_news)
                R.id.mnDanhMuc -> {
                    val intent = Intent(this@MainActivity, FootlballActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.mnTinHot -> {
                    val intent = Intent(this@MainActivity, WeatherActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.mnMenu -> {
                    loadFragment(fragment_search)
                }
                else -> {
                    loadFragment(fragment_news)
                    return@setOnItemSelectedListener true
                }
            }
            true
        }

    }

    private fun loadFragment(fragmentReplace: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragmentReplace)
            .commit()
    }

}