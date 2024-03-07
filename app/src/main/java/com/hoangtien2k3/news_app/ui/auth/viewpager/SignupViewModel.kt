package com.hoangtien2k3.news_app.ui.auth.viewpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.auth.signup.SignupClient
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.network.response.SignupResponse
import com.hoangtien2k3.news_app.network.result.SignupResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val _signupResult = MutableLiveData<SignupResult>()
    val signupResult: LiveData<SignupResult> = _signupResult

    fun signup(name: String, username: String, email: String, password: String, roles: Set<String> = setOf("USER")) {
        val signRequest = SignupRequest(name, username, email, password, roles)
        val service = SignupClient.apiService
        val call: Call<SignupResponse> = service.signup(signRequest)

        call.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    _signupResult.value = loginResponse?.let {
                        SignupResult.Success(it)
                    }
                } else {
                    _signupResult.value = SignupResult.Error("Signup failed")
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                _signupResult.value = SignupResult.Error("Network error")
            }
        })
    }
}