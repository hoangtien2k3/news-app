package com.hoangtien2k3.news_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.models.Football

class NewsFootballViewModel : ViewModel() {
    var ItemList: MutableLiveData<List<Football>>? = null
    private val datas: ArrayList<Football> = ArrayList<Football>()

    fun getHeroes(): LiveData<List<Football>>? {

        if (ItemList == null) {
            ItemList = MutableLiveData<List<Football>>()

        }

        return ItemList
    }

    fun addItem(modelNews: Football) {

        datas.add(modelNews)

    }


    fun SetItem(datas2: List<Football>) {

        ItemList!!.value = datas2

    }
}