package com.example.shopmart.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.repository.cart.CartRepository
import com.example.shopmart.ui.base.BaseViewModel

class CartViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    val cartLiveData = MutableLiveData<List<Cart>>()

    init {
        getCart()
    }

    fun getCart() {
        launch {
            cartLiveData.value = cartRepository.getCartList()
        }
    }

    fun removeToCart(cart: Cart, position: Int) {
        launch {
            if (cart.quantity <= 1) {
                cartRepository.removeToCart(cart)
                cartLiveData.value = cartLiveData.value?.toMutableList()?.apply {
                    remove(cart)
                }
            } else {
                cartRepository.minusToCart(cart)
                cartLiveData.value = cartLiveData.value?.apply {
                    get(position).quantity -= 1
                }
            }
        }
    }

    fun addToCart(cart: Cart, position: Int) {
        launch {
            cartRepository.plusToCart(cart)
            cartLiveData.value = cartLiveData.value?.apply {
                get(position).quantity += 1
            }
        }
    }
}