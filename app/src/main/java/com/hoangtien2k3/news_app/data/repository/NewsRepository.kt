package com.hoangtien2k3.news_app.data.repository

import com.hoangtien2k3.news_app.data.source.api.RetrofitInstance
import com.hoangtien2k3.news_app.data.source.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

}