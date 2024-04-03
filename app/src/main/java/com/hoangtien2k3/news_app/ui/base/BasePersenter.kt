package com.hoangtien2k3.news_app.ui.base

interface BasePersenter {
    fun showLoading()
    fun hideLoading()
    fun responseError(error: String)
}