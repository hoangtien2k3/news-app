package com.hoangtien2k3.news_app.ui.menu.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import com.hoangtien2k3.news_app.network.request.ChangePasswordRequest
import com.hoangtien2k3.news_app.network.response.ChangePasswordResponse
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {

    private val _changePasswordResult = MutableLiveData<Resource<ChangePasswordResponse>>()
    val changePasswordResult: LiveData<Resource<ChangePasswordResponse>> = _changePasswordResult

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
        val service = AccountClient.apiService
        val call: Call<ChangePasswordResponse> = service.changePassword(changePasswordRequest)

        call.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {
                    val changePassResponse = response.body()
                    _changePasswordResult.value = changePassResponse?.let {
                        Resource.Success(it)
                    }
                } else {
                    _changePasswordResult.value = Resource.Error(R.string.change_password_failed.toString())
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _changePasswordResult.value = Resource.Error(R.string.network_error.toString())
            }
        })
    }
}