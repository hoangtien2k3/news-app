package com.hoangtien2k3.news_app.data.models

data class Category (
    val name: String,
    val image: Int,
    var category: String
) {
    constructor(name: String, category: String) : this(name, 0, category)
}
