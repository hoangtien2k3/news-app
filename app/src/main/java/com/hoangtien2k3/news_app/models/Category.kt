package com.hoangtien2k3.news_app.models

data class Category(
    val name: String,
    val image: Int,
    var url: String
) {
    constructor(name: String, url: String) : this(name, 0, url)
}
