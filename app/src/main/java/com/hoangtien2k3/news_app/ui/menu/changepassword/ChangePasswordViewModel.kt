package com.hoangtien2k3.news_app.ui.menu.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.ChangePasswordRequest
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource

class ChangePasswordViewModel : BaseViewModelImpl() {
    private val _changePasswordResult = MutableLiveData<Resource<ApiResponse<UserResponse>>>()
    val changePasswordResult: LiveData<Resource<ApiResponse<UserResponse>>> = _changePasswordResult

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
        performAction(_changePasswordResult) {
            apiService.changePassword(changePasswordRequest)
        }
    }
}