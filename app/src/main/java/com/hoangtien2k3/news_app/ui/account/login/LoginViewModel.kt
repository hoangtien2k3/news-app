package com.hoangtien2k3.news_app.ui.account.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.response.LoginResponse
import com.hoangtien2k3.news_app.data.source.auth.login.LoginClient
import com.hoangtien2k3.news_app.network.response.LoginResponseError
import com.hoangtien2k3.news_app.network.result.LoginResult
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
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, LoginResponseError::class.java)
                    _loginResult.value = LoginResult.Error(errorResponse)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = LoginResult.Error(LoginResponseError("onFailure","BAD_REQUEST","Network error"))
            }
        })
    }
}

