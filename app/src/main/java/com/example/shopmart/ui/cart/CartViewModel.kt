package com.example.shopmart.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.repository.cart.CartRepository
import com.example.shopmart.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class CartViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    var loadingLiveData = MutableLiveData<Boolean>()

    val cartLiveData = MutableLiveData<List<Cart>>()

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            loadingLiveData.value = true
            cartLiveData.value = cartRepository.getCartList()
            loadingLiveData.value = false
        }
    }
}