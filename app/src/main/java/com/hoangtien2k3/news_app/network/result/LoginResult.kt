package com.hoangtien2k3.news_app.network.result

import com.hoangtien2k3.news_app.network.response.LoginResponse

sealed class LoginResult {
    data class Success(val loginResponse: LoginResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
