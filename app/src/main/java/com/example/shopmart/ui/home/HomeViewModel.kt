package com.example.shopmart.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.ui.base.BaseViewModel

class HomeViewModel @ViewModelInject constructor(): BaseViewModel() {

    val pageLiveData = MutableLiveData<Int>()

    fun setPage(page: Int) {
        pageLiveData.value = page
    }
}