package com.hoangtien2k3.news_app.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.api.FootballService
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import androidx.lifecycle.viewModelScope
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.models.Category
import kotlinx.coroutines.launch

import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _footballNews = MutableLiveData<List<Football>>()
    val footballNews: LiveData<List<Football>> = _footballNews

    init {
        fetchCategories()
        fetchFootballNews()
    }

    private fun fetchCategories() {
        val categoriesList: List<Category> = getListDanhMuc()
        _categories.value = categoriesList
    }

    private fun fetchFootballNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_Foolball)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(FootballService::class.java)
            val call = service.getFootballData()

            call.enqueue(object : Callback<List<Football>> {
                override fun onResponse(call: Call<List<Football>>, response: Response<List<Football>>) {
                    if (response.isSuccessful) {
                        val footballList = response.body() ?: emptyList()
                        _footballNews.postValue(footballList)
                    }
                }

                override fun onFailure(call: Call<List<Football>>, t: Throwable) {

                }
            })
        }
    }

    private fun getListDanhMuc(): List<Category> {
        return listOf(
            Category("Nổi Bật", Constants.BASE_URL_TIN_NOI_BAT),
            Category("Mới Nhất", Constants.BASE_URL_TIN_MOI_NHAT),
            Category("Thế Giới", Constants.BASE_URL_TIN_THE_GIOI),
            Category("Khởi Nghiệp", Constants.BASE_URL_TIN_STARUP),
            Category("Giải Trí", Constants.BASE_URL_TIN_GIAI_TRI),
            Category("Thể Thao", Constants.BASE_URL_TIN_THE_THAO),
            Category("Pháp Luật", Constants.BASE_URL_TIN_PHAP_LUAT),
            Category("Giáo Dục", Constants.BASE_URL_TIN_GIAO_DUC),
            Category("Sức Khỏe", Constants.BASE_URL_TIN_SUC_KHOE),
            Category("Đời Sống", Constants.BASE_URL_TIN_DOI_SONG),
            Category("Khoa Học", Constants.BASE_URL_TIN_KHOA_HOC),
            Category("Kinh Doanh", Constants.BASE_URL_TIN_KINH_DOANH),
            Category("Tâm Sự", Constants.BASE_URL_TIN_TAM_SU),
            Category("Số Hóa", Constants.BASE_URL_TIN_SO_HOA),
            Category("Du Lịch", Constants.BASE_URL_TIN_DU_LICH)
        )
    }


}
