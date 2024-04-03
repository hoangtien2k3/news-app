package com.hoangtien2k3.news_app.network.request

data class IntrospectRequest(
    var authenticated: Boolean,
    var token: String
)