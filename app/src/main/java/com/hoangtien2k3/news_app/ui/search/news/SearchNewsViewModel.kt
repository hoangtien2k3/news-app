package com.hoangtien2k3.news_app.ui.search.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hoangtien2k3.news_app.data.models.NewsResponse
import com.hoangtien2k3.news_app.data.repository.NewsRepository
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchNewsViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModelImpl() {
    private val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var breakingNewsPage = 1

    //for search news
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchNewsPage = 1

    init {
        getBreakingNews("in")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}