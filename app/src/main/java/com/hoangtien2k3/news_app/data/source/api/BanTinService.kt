package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.data.models.BanTin
import retrofit2.Call
import retrofit2.http.GET

interface BanTinService {
    @GET("/api/news")
    fun getFullBanTinData(): Call<List<BanTin>>
    @GET("api/news/category/tin-noi-bat")
    fun getTinNoiBat(): Call<List<BanTin>>
    @GET("api/news/category/tin-moi-nhat")
    fun getTinMoiNhat(): Call<List<BanTin>>
    @GET("api/news/category/tin-the-gioi")
    fun getTheGioi(): Call<List<BanTin>>
    @GET("api/news/category/tin-the-thao")
    fun getTheThao(): Call<List<BanTin>>
    @GET("api/news/category/tin-phap-luat")
    fun getPhapLuat(): Call<List<BanTin>>
    @GET("api/news/category/tin-giao-duc")
    fun getGiaoDuc(): Call<List<BanTin>>
    @GET("api/news/category/tin-suc-khoe")
    fun getSucKhoe(): Call<List<BanTin>>
    @GET("api/news/category/tin-doi-song")
    fun getDoiSong(): Call<List<BanTin>>
    @GET("api/news/category/tin-khoa-hoc")
    fun getKhoaHoc(): Call<List<BanTin>>
    @GET("api/news/category/tin-kinh-doanh")
    fun getKinhDoanh(): Call<List<BanTin>>
    @GET("api/news/category/tin-tam-su")
    fun getTamSu(): Call<List<BanTin>>
    @GET("api/news/category/tin-so-hoa")
    fun getSoHoa(): Call<List<BanTin>>
    @GET("api/news/category/tin-du-lich")
    fun getDuLich(): Call<List<BanTin>>
}