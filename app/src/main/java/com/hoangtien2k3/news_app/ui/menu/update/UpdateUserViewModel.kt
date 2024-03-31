package com.hoangtien2k3.news_app.ui.menu.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.UpdateUserRequest
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUserViewModel : ViewModel(){
    private val _updateUser = MutableLiveData<Resource<ApiResponse<UserResponse>>>()
    val updateUser: LiveData<Resource<ApiResponse<UserResponse>>> = _updateUser

    fun updateUserInfo(name: String, username: String, email: String, password: String) {
        val updateUserRequest = UpdateUserRequest(name, email, password)
        val userId = DataLocalManager.getInstance().getInfoUserId()
        val service = AccountClient.apiService
        val call: Call<ApiResponse<UserResponse>> = service.updateUser(userId, updateUserRequest)

        call.enqueue(object : Callback<ApiResponse<UserResponse>> {
            override fun onResponse(call: Call<ApiResponse<UserResponse>>, response: Response<ApiResponse<UserResponse>>) {
                val updateUserResponse = response.body()
                if (response.isSuccessful) {
                    _updateUser.value = updateUserResponse?.let {
                        Resource.Success(it)
                    }
                } else {
                    _updateUser.value =Resource.Error(R.string.update_info_failed.toString())
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserResponse>>, t: Throwable) {
                _updateUser.value = Resource.Error(R.string.network_error.toString())
            }
        })
    }

}