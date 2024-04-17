package com.hoangtien2k3.news_app.ui.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.request.SavePosRequest
import com.hoangtien2k3.news_app.network.response.SavePosResponse
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource

class SaveBanTinViewModel : BaseViewModelImpl() {
    private val _saveBanTin = MutableLiveData<Resource<SavePosResponse>>()
    val saveBanTin: LiveData<Resource<SavePosResponse>> = _saveBanTin

    private val _getSaveBanTin = MutableLiveData<Resource<List<SavePosResponse>>>()
    val getSaveBanTin: LiveData<Resource<List<SavePosResponse>>> = _getSaveBanTin

    private val _deleteAllBanTin = MutableLiveData<Resource<Void>>()
    val deleteAllBanTin: LiveData<Resource<Void>> = _deleteAllBanTin

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)


    // save Ban Tin call backend
    fun postNewsSave(title: String, link: String, img: String, pubDate: String, userId: Long) {
        val saveBanTinRequest = SavePosRequest(title, link, img, pubDate, userId)
        performAction(_saveBanTin) {
            apiService.postNewsSave(saveBanTinRequest)
        }
    }

    // get bản tin call backend local
    fun getListAllNewsSave() {
        performAction(_getSaveBanTin) {
            val userId = DataLocalManager.getInstance().getInfoUserId()
            apiService.getAllNewsSaveByUserId(userId)
        }
    }

    // delete all bản tin đã đọc
    fun deleteAllListNewsSave() {
        performAction(_deleteAllBanTin) {
            apiService.deleteAllPostNews()
        }
    }

}