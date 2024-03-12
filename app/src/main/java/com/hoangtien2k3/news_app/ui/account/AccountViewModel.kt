package com.hoangtien2k3.news_app.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.network.response.LoginResponse
import com.hoangtien2k3.news_app.network.response.SignupResponse
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Resource<LoginResponse>>()
    val loginResult: LiveData<Resource<LoginResponse>> = _loginResult

    private val _signupResult = MutableLiveData<Resource<SignupResponse>>()
    val signupResult: LiveData<Resource<SignupResponse>> = _signupResult

    fun login(username: String, password: String) {
        performAction(_loginResult) {
            createLoginCall(LoginRequest(username, password))
        }
    }

    fun signup(name: String, username: String, email: String, password: String, roles: Set<String> = setOf("USER")) {
        performAction(_signupResult) {
            createSignupCall(SignupRequest(name, username, email, password, roles))
        }
    }

    private fun <T> performAction(resultLiveData: MutableLiveData<Resource<T>>, apiCall: () -> Call<T>) {
        resultLiveData.value = Resource.Loading()
        val call = apiCall.invoke()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.let {
                        resultLiveData.value = Resource.Success(it)
                    }
                } else {
                    resultLiveData.value = Resource.Error("Request failed")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                resultLiveData.value = Resource.Error("Network error")
            }
        })
    }

    private fun createLoginCall(request: LoginRequest): Call<LoginResponse> {
        return AccountClient.apiService.login(request)
    }

    private fun createSignupCall(request: SignupRequest): Call<SignupResponse> {
        return AccountClient.apiService.signup(request)
    }
}
