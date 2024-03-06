package com.hoangtien2k3.news_app.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.response.LoginRequest
import com.hoangtien2k3.news_app.data.models.LoginResponse
import com.hoangtien2k3.news_app.data.source.auth.login.LoginClient
import com.hoangtien2k3.news_app.response.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)
        val service = LoginClient.apiService
        val call: Call<LoginResponse> = service.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    _loginResult.value = loginResponse?.let {
                        LoginResult.Success(it)
                    }
                } else {
                    _loginResult.value = LoginResult.Error("Login failed")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = LoginResult.Error("Network error")
            }
        })
    }
}
