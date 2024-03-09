package com.hoangtien2k3.news_app.network.request

data class UpdateUserRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val roles: Set<String>
)