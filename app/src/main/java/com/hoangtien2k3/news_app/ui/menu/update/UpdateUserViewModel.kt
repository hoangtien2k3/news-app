package com.hoangtien2k3.news_app.ui.menu.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import com.hoangtien2k3.news_app.network.request.UpdateUserRequest
import com.hoangtien2k3.news_app.network.response.UpdateUserResponse
import com.hoangtien2k3.news_app.network.result.UpdateUserResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUserViewModel : ViewModel(){
    private val _updateUser = MutableLiveData<UpdateUserResult>()
    val updateUser: LiveData<UpdateUserResult> = _updateUser

    fun updateUserInfo(name: String, username: String, email: String, password: String) {
        val updateUserRequest = UpdateUserRequest(name, username, email, password, setOf("USER"))
        val userId = DataLocalManager.getInstance().getInfoUserId()
        val service = AccountClient.apiService
        val call: Call<UpdateUserResponse> = service.updateUser(userId, updateUserRequest)

        call.enqueue(object : Callback<UpdateUserResponse> {
            override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                if (response.isSuccessful) {
                    val updateUserResponse = response.body()
                    _updateUser.value = updateUserResponse?.let {
                        UpdateUserResult.Success(it)
                    }
                } else {
                    _updateUser.value = UpdateUserResult.Error(R.string.update_info_failed.toString())
                }
            }

            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                _updateUser.value = UpdateUserResult.Error(R.string.network_error.toString())
            }
        })
    }

}