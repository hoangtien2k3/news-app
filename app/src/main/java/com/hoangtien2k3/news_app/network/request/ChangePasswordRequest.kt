package com.hoangtien2k3.news_app.network.request

data class ChangePasswordRequest(
    val name: String,
    val email: String,
    val password: String
)