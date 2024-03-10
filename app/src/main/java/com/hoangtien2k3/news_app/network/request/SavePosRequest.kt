package com.hoangtien2k3.news_app.network.request

data class SavePosRequest(
    val title: String,
    val link: String,
    val img: String,
    val pubDate: String,
    val userId: Long
)
