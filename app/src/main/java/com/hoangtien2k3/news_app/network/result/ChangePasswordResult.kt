package com.hoangtien2k3.news_app.network.result

import com.hoangtien2k3.news_app.network.response.ChangePasswordResponse

sealed class ChangePasswordResult {
    data class Success(val changePasswordResponse: ChangePasswordResponse) : ChangePasswordResult()
    data class Error(val changePasswordResponse: ChangePasswordResponse) : ChangePasswordResult()
}