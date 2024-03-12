package com.hoangtien2k3.news_app.ui.menu.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.R
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteUserViewModel : ViewModel() {
    private val _deleteUser = MutableLiveData<String>()
    val deleteUser: LiveData<String> = _deleteUser

    fun deleteUserId(id: Long) {
        val deleteAccountService = AccountClient.apiService
        val call: Call<String> = deleteAccountService.delete(id)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    _deleteUser.value = R.string.delete_successfully.toString()
                } else {
                    _deleteUser.value = R.string.delete_failed.toString()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _deleteUser.value = R.string.error_systeam.toString()
            }
        })
    }

}