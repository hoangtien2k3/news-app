package com.hoangtien2k3.news_app.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.auth.AccountClient
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.AuthenticationResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date


class AccountViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Resource<ApiResponse<AuthenticationResponse>>>()
    val loginResult: LiveData<Resource<ApiResponse<AuthenticationResponse>>> = _loginResult

    private val _signupResult = MutableLiveData<Resource<ApiResponse<UserResponse>>>()
    val signupResult: LiveData<Resource<ApiResponse<UserResponse>>> = _signupResult

    fun login(username: String, password: String) {
        performAction(_loginResult) {
            createLoginCall(LoginRequest(username, password))
        }
    }

    fun signup(name: String, username: String, email: String, password: String, roles: Set<String> = setOf(Constants.ROLE_USER)) {
        performAction(_signupResult) {
            createSignupCall(SignupRequest(name, username, email, password, roles))
        }
    }

    private fun <T> performAction(resultLiveData: MutableLiveData<Resource<T>>, apiCall: () -> Call<T>) {
        resultLiveData.value = Resource.Loading()
        val call = apiCall()
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

    private fun createLoginCall(request: LoginRequest): Call<ApiResponse<AuthenticationResponse>> {
        return AccountClient.apiService.login(request)
    }

    private fun createSignupCall(request: SignupRequest): Call<ApiResponse<UserResponse>> {
        return AccountClient.apiService.signup(request)
    }
}
