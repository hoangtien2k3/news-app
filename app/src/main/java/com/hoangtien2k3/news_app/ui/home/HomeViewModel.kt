package com.hoangtien2k3.news_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.Football
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.hoangtien2k3.news_app.data.models.Category
import com.hoangtien2k3.news_app.data.source.api.FootballClient
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
            val service = FootballClient.apiService
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
            Category("Nổi Bật", "tin-noi-bat"),
            Category("Mới Nhất", "tin-moi-nhat"),
            Category("Thế Giới", "tin-the-gioi"),
            Category("Thể Thao", "tin-the-thao"),
            Category("Pháp Luật", "tin-phap-luat"),
            Category("Giáo Dục", "tin-giao-duc"),
            Category("Sức Khỏe", "tin-suc-khoe"),
            Category("Đời Sống", "tin-doi-song"),
            Category("Khoa Học", "tin-khoa-hoc"),
            Category("Kinh Doanh", "tin-kinh-doanh"),
            Category("Tâm Sự", "tin-tam-su"),
            Category("Số Hóa", "tin-so-hoa"),
            Category("Du Lịch", "tin-du-lich")
//            Category("Khởi Nghiệp", "startup"), // null
//            Category("Giải Trí", "tin-giai-tri"), // null
        )
    }

}
