package com.hoangtien2k3.news_app.network

import java.time.LocalDateTime

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T
)

data class UserResponse (
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val role: Set<String>,
    val timestamps: Timestamps
)

data class Timestamps(
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime
)

data class AuthenticationResponse(
    val authenticated: Boolean,
    val token: String
)

data class IntrospectResponse(
    val valid: Boolean
)

data class Error(
    val code: Int,
    val message: String
)