package com.hoangtien2k3.news_app.network.response

data class LoginResponse(
    val id: Int,
    val token: String,
    val type: String,
    val name: String,
    val roles: List<Role>
)

data class Role(
    val authority: String
)
