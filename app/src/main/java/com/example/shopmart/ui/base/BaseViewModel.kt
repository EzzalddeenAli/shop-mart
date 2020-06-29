package com.example.shopmart.ui.base

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmart.Event
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel @ViewModelInject() constructor() : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()

    val errorLiveData = MutableLiveData<Throwable>()

    val snackBarLiveData = MutableLiveData<Event<Int>>()

    protected fun launch(function: suspend () -> Unit) {
        viewModelScope.launch {
            loadingLiveData.value = true
            kotlin.runCatching {
                function()
                loadingLiveData.value = false
            }.onFailure {
                Timber.e(it)
                errorLiveData.value = it
                loadingLiveData.value = false
            }
        }
    }
}