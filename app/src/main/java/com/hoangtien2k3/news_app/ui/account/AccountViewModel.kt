package com.hoangtien2k3.news_app.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.AuthenticationResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call

class AccountViewModel : BaseViewModelImpl() {
    private val _loginResult = MutableLiveData<Resource<ApiResponse<AuthenticationResponse>>>()
    val loginResult: LiveData<Resource<ApiResponse<AuthenticationResponse>>> = _loginResult

    private val _signupResult = MutableLiveData<Resource<ApiResponse<UserResponse>>>()
    val signupResult: LiveData<Resource<ApiResponse<UserResponse>>> = _signupResult

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

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

    private fun createLoginCall(request: LoginRequest): Call<ApiResponse<AuthenticationResponse>> {
        return apiService.login(request)
    }

    private fun createSignupCall(request: SignupRequest): Call<ApiResponse<UserResponse>> {
        return apiService.signup(request)
    }
}
