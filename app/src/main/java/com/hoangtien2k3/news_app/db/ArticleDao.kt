package com.hoangtien2k3.news_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hoangtien2k3.news_app.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}