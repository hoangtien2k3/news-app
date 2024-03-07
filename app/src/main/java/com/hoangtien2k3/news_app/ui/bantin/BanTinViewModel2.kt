package com.hoangtien2k3.news_app.ui.bantin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.source.api.BanTinClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanTinViewModel2 : ViewModel() {

    private val _banTinNews = MutableLiveData<List<BanTin>>()
    val listTinTuc: LiveData<List<BanTin>> = _banTinNews

    fun fetchListTinTuc(banTin: String) {

        val service = BanTinClient.apiService
        val call: Call<List<BanTin>> = when(banTin) {
            "tin-noi-bat" -> service.getTinNoiBat()
            "tin-moi-nhat" -> service.getTinMoiNhat()
            "tin-the-gioi" -> service.getTheGioi()
            "tin-the-thao" -> service.getTheThao()
            "tin-phap-luat" -> service.getPhapLuat()
            "tin-giao-duc" -> service.getGiaoDuc()
            "tin-suc-khoe" -> service.getSucKhoe()
            "tin-doi-song" -> service.getDoiSong()
            "tin-khoa-hoc" -> service.getKhoaHoc()
            "tin-kinh-doanh" -> service.getKinhDoanh()
            "tin-tam-su" -> service.getTamSu()
            "tin-so-hoa" -> service.getDuLich()
            "tin-du-lich" -> service.getDuLich()
//            "startup" -> service.getStartup()
//            "giai-tri" -> service.getGiaiTri()
            else -> throw IllegalArgumentException("Unsupported news type")
        }

        call.enqueue(object : Callback<List<BanTin>> {
            override fun onResponse(call: Call<List<BanTin>>, response: Response<List<BanTin>>) {
                if (response.isSuccessful) {
                    val footballList = response.body() ?: emptyList()
                    _banTinNews.value = footballList
                }
            }

            override fun onFailure(call: Call<List<BanTin>>, t: Throwable) {

            }
        })
    }

}