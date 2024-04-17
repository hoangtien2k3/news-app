package com.hoangtien2k3.news_app.ui.menu.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.UpdateUserRequest
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource

class UpdateUserViewModel : BaseViewModelImpl() {
    private val _updateUser = MutableLiveData<Resource<ApiResponse<UserResponse>>>()
    val updateUser: LiveData<Resource<ApiResponse<UserResponse>>> = _updateUser

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    fun updateUserInfo(name: String, username: String, email: String, password: String) {
        val updateUserRequest = UpdateUserRequest(name, email, password)
        val userId = DataLocalManager.getInstance().getInfoUserId()
        performAction(_updateUser) {
            apiService.updateUser(userId, updateUserRequest)
        }
    }
}