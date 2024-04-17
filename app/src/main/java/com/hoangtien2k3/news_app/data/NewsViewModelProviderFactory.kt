package com.hoangtien2k3.news_app.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hoangtien2k3.news_app.data.repository.NewsRepository
import com.hoangtien2k3.news_app.ui.search.news.SearchNewsViewModel

class NewsViewModelProviderFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchNewsViewModel(newsRepository) as T
    }
}