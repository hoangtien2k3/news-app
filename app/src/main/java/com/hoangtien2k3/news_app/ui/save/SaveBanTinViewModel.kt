package com.hoangtien2k3.news_app.ui.save

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.data.source.save.SaveBanTinClient
import com.hoangtien2k3.news_app.network.request.SavePosRequest
import com.hoangtien2k3.news_app.network.response.SavePosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveBanTinViewModel : ViewModel() {
    private val _saveBanTin = MutableLiveData<SavePosResponse?>()
    val saveBanTin: LiveData<SavePosResponse?> = _saveBanTin

    private val _getSaveBanTin = MutableLiveData<List<SavePosResponse>?>()
    val getSaveBanTin: LiveData<List<SavePosResponse>?> = _getSaveBanTin

    private val _deleteAllBanTin = MutableLiveData<Void?>()
    val deleteAllBanTin: LiveData<Void?> = _deleteAllBanTin

    // save Ban Tin call backend
    fun postNewsSave(title: String, link: String, img: String, pubDate: String, userId: Long) {
        val saveBanTinRequest = SavePosRequest(title, link, img, pubDate, userId)
        val service = SaveBanTinClient.apiService
        val call: Call<SavePosResponse> = service.postNewsSave(saveBanTinRequest)

        call.enqueue(object : Callback<SavePosResponse> {
            override fun onResponse(call: Call<SavePosResponse>, response: Response<SavePosResponse>) {
                if (response.isSuccessful) {
                    val savePostResponse = response.body()
                    _saveBanTin.value = savePostResponse
                } else {
//                    _loginResult.value = savePostResponse
                }
            }

            override fun onFailure(call: Call<SavePosResponse>, t: Throwable) {
//                _saveBanTin.value = null
            }
        })
    }


    // get bản tin call backend local
    fun getListAllNewsSave() {
        // lấy ra userId của người dùng
        val userId = DataLocalManager.getInstance().getInfoUserId()
        val service = SaveBanTinClient.apiService
        val call: Call<List<SavePosResponse>> = service.getAllNewsSaveByUserId(userId)

        call.enqueue(object : Callback<List<SavePosResponse>> {
            override fun onResponse(call: Call<List<SavePosResponse>>, response: Response<List<SavePosResponse>>) {
                if (response.isSuccessful) {
                    val saveListPostResponse = response.body()
                    _getSaveBanTin.value = saveListPostResponse
                    Log.d("CallSave", saveListPostResponse.toString())
                } else {
//                    _loginResult.value = savePostResponse
                }
            }

            override fun onFailure(call: Call<List<SavePosResponse>>, t: Throwable) {
//                _saveBanTin.value = null
            }
        })
    }


    // delete all bản tin đã đọc
    fun deleteAllListNewsSave() {
        val service = SaveBanTinClient.apiService
        val call: Call<Void> = service.deleteAllPostNews()

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val saveListPostResponse = response.body()
                    _deleteAllBanTin.value = saveListPostResponse
                    Log.d("CallSave", saveListPostResponse.toString())
                } else {
//                    _loginResult.value = savePostResponse
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
//                _saveBanTin.value = null
            }
        })
    }

}