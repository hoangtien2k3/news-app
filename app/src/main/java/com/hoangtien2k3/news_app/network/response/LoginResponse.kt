package com.hoangtien2k3.news_app.network.response

data class LoginResponse(
    val id: Long,
    val token: String,
    val type: String,
    val name: String,
    val username: String,
    val email: String,
    val roles: List<Role>
)

data class Role(
    val authority: String
)

data class LoginResponseError(
    val timestamp: String,
    val httpStatus: String,
    val msg: String
)