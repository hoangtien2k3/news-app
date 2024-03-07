package com.hoangtien2k3.news_app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.ActivityMainBinding
import com.hoangtien2k3.news_app.data.source.db.ArticleDatabase
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.menu.MenuFragment
import com.hoangtien2k3.news_app.ui.home.HomeFragment
import com.hoangtien2k3.news_app.ui.search.SearchNewsFragment
import com.hoangtien2k3.news_app.data.repository.NewsRepository
import com.hoangtien2k3.news_app.ui.search.SearchNewsViewModel
import com.hoangtien2k3.news_app.data.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: SearchNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[SearchNewsViewModel::class.java]

        loadFragment(MainFragment())
        clickButtonNavigation()
    }

    private fun clickButtonNavigation() {
        binding.navMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> loadFragment(MainFragment())
                R.id.bottom_category -> loadFragment(BanTinFragment())
                R.id.bottom_save -> loadFragment(FootballFragment())
                R.id.bottom_search -> loadFragment(SearchNewsFragment())
                R.id.bottom_profile -> loadFragment(MenuFragment())
                else -> loadFragment(HomeFragment())
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
