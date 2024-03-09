package com.hoangtien2k3.news_app.network.request

data class PostNewsLetterRequest(
    val title: String,
    val link: String,
    val img: String,
    val pubDate: String,
    val category: String
)