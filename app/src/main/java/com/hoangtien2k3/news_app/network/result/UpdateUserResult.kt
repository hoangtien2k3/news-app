package com.hoangtien2k3.news_app.network.result

import com.hoangtien2k3.news_app.network.response.UpdateUserResponse

sealed class UpdateUserResult {
    data class Success(val updateUserResponse: UpdateUserResponse) : UpdateUserResult()
    data class Error(val message: String) : UpdateUserResult()
}