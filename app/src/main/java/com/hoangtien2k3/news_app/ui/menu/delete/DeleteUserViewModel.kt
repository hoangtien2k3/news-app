package com.hoangtien2k3.news_app.ui.menu.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource

class DeleteUserViewModel : BaseViewModelImpl() {
    private val _deleteUser = MutableLiveData<Resource<String>>()
    val deleteUser: LiveData<Resource<String>> = _deleteUser

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    fun deleteUserId(id: Long) {
        performAction(_deleteUser) {
            apiService.delete(id)
        }
    }

}