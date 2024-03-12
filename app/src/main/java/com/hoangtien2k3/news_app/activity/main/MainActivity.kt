package com.hoangtien2k3.news_app.activity.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.databinding.ActivityMainBinding
import com.hoangtien2k3.news_app.data.source.db.ArticleDatabase
import com.hoangtien2k3.news_app.ui.menu.MenuFragment
import com.hoangtien2k3.news_app.ui.home.fragment.HomeFragment
import com.hoangtien2k3.news_app.ui.search.SearchNewsFragment
import com.hoangtien2k3.news_app.data.repository.NewsRepository
import com.hoangtien2k3.news_app.ui.search.SearchNewsViewModel
import com.hoangtien2k3.news_app.data.NewsViewModelProviderFactory
import com.hoangtien2k3.news_app.ui.bantin.BanTinFragment
import com.hoangtien2k3.news_app.ui.football.FootballFragment
import com.hoangtien2k3.news_app.ui.home.MainFragment
import com.hoangtien2k3.news_app.ui.save.SaveBanTinFragment
import com.hoangtien2k3.news_app.ui.search.bantin.SearchBanTinFragment
import com.hoangtien2k3.news_app.utils.Constants

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
                R.id.bottom_category -> loadFragment(BanTinFragment(Constants.full))
                R.id.bottom_save -> { loadFragment(SaveBanTinFragment()) }
                R.id.bottom_search -> loadFragment(SearchBanTinFragment(Constants.full))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                mBanTinAdapter.filter.filter(query)
//                loadFragment(SearchFragment("full"))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                mBanTinAdapter.filter.filter(newText)
                loadFragment(SearchBanTinFragment("full"))
                return true
            }
        })
        return true
    }

}
