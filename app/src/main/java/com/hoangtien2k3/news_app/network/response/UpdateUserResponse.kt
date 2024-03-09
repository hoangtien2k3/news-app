package com.hoangtien2k3.news_app.network.response

data class UpdateUserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val roles: Set<String>
)