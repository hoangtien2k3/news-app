package com.hoangtien2k3.news_app.data.repository

import com.hoangtien2k3.news_app.data.source.api.RetrofitNews
import com.hoangtien2k3.news_app.data.source.db.ArticleDatabase
import com.hoangtien2k3.news_app.data.source.db.NewsAPI

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitNews.apiService(NewsAPI::class.java).getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitNews.apiService(NewsAPI::class.java).searchForNews(searchQuery, pageNumber)

}