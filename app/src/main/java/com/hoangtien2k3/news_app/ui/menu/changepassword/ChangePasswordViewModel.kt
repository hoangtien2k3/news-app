package com.hoangtien2k3.news_app.ui.menu.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import com.hoangtien2k3.news_app.network.request.ChangePasswordRequest
import com.hoangtien2k3.news_app.network.response.ChangePasswordResponse
import com.hoangtien2k3.news_app.network.result.ChangePasswordResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {

    private val _changPasswordResult = MutableLiveData<ChangePasswordResult>()
    val changPasswordResult: LiveData<ChangePasswordResult> = _changPasswordResult

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
        val service = AccountClient.apiService
        val call: Call<ChangePasswordResponse> = service.changePassword(changePasswordRequest)

        call.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {
                    val changepassResponse = response.body()
                    _changPasswordResult.value = changepassResponse?.let {
                        ChangePasswordResult.Success(it)
                    }
                } else {
                    _changPasswordResult.value = ChangePasswordResult.Error(ChangePasswordResponse("Thay đổi mật khẩu không thành công"))
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _changPasswordResult.value = ChangePasswordResult.Error(ChangePasswordResponse("Network error"))
            }
        })
    }

}