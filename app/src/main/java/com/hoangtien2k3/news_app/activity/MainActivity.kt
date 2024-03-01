package com.hoangtien2k3.news_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.ActivityMainBinding
import com.hoangtien2k3.news_app.db.ArticleDatabase
import com.hoangtien2k3.news_app.fragment.BanTinFragment
import com.hoangtien2k3.news_app.fragment.MenuFragment
import com.hoangtien2k3.news_app.fragment.HomeFragment
import com.hoangtien2k3.news_app.fragment.SearchNewsFragment
import com.hoangtien2k3.news_app.repository.NewsRepository
import com.hoangtien2k3.news_app.viewmodel.NewsViewModel
import com.hoangtien2k3.news_app.viewmodel.NewsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // Khai báo biến ViewBinding

    private lateinit var fragment_news: Fragment
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Khởi tạo binding

        setContentView(binding.root) // Sử dụng root của binding làm layout chính

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        clickButtonNavigation()
    }

    private fun clickButtonNavigation() {
        binding.navMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> loadFragment(HomeFragment())
                R.id.bottom_category -> loadFragment(BanTinFragment())
                R.id.bottom_save -> {
                    val intent = Intent(this@MainActivity, FootlballActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
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
