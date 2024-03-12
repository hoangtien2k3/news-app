package com.hoangtien2k3.news_app.ui.bantin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.source.api.BanTinClient
import com.hoangtien2k3.news_app.data.source.api.BanTinService
import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanTinViewModel : ViewModel() {
    private val _banTinNews = MutableLiveData<List<BanTin>>()
    val listTinTuc: LiveData<List<BanTin>> = _banTinNews

    fun fetchListTinTuc(banTin: String) {
        val service = BanTinClient.apiService
        val call: Call<List<BanTin>> = getServiceCall(service, banTin)

        call.enqueue(object : Callback<List<BanTin>> {
            override fun onResponse(call: Call<List<BanTin>>, response: Response<List<BanTin>>) {
                if (response.isSuccessful) {
                    val banTinList = response.body() ?: emptyList()
                    _banTinNews.value = banTinList
                }
            }

            override fun onFailure(call: Call<List<BanTin>>, t: Throwable) {
                // Xử lý lỗi khi request thất bại nếu cần
            }
        })
    }

    private fun getServiceCall(service: BanTinService, banTin: String): Call<List<BanTin>> {
        return when (banTin) {
            Constants.tin_noi_bat -> service.getTinNoiBat()
            Constants.tin_moi_nhat -> service.getTinMoiNhat()
            Constants.tin_the_gioi  -> service.getTheGioi()
            Constants.tin_the_thao -> service.getTheThao()
            Constants.tin_phap_luat  -> service.getPhapLuat()
            Constants.tin_giao_duc  -> service.getGiaoDuc()
            Constants.tin_suc_khoe  -> service.getSucKhoe()
            Constants.tin_doi_song  -> service.getDoiSong()
            Constants.tin_khoa_hoc  -> service.getKhoaHoc()
            Constants.tin_kinh_doanh  -> service.getKinhDoanh()
            Constants.tin_tam_su  -> service.getTamSu()
            Constants.tin_so_hoa  -> service.getSoHoa()
            Constants.tin_du_lich  -> service.getDuLich()
            Constants.full  -> service.getFullBanTinData()
            else -> service.getFullBanTinData()
        }
    }
}
