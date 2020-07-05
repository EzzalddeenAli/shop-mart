package com.example.shopmart.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.repository.cart.CartRepository
import com.example.shopmart.exception.EmptyCart
import com.example.shopmart.ui.base.BaseViewModel

class CartViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    val cartLiveData = MutableLiveData<MutableList<Cart>>()

    val confirmRemoveToCartLiveData = MutableLiveData<Cart>()

    init {
        getCart()
    }

    fun getCart() {
        launch {
            cartLiveData.value = cartRepository.getCartList().toMutableList()
        }
    }

    fun minusToCart(cart: Cart, position: Int) {
        if (cart.quantity <= 1) {
            confirmRemoveToCartLiveData.value = cart
        } else {
            launch(allowMultipleRequest = false) {
                cartRepository.minusToCart(cart)
                cartLiveData.value = cartLiveData.value?.apply {
                    get(position).quantity -= 1
                }
            }
        }
    }

    fun removeToCart(cart: Cart) {
        launch(allowMultipleRequest = false) {
            cartRepository.removeToCart(cart)
            cartLiveData.value = cartLiveData.value?.apply {
                remove(cart)
            }
            checkCartList()
        }
    }

    private fun checkCartList() {
        if (cartLiveData.value.isNullOrEmpty()) {
            errorLiveData.value = EmptyCart()
        }
    }

    fun addToCart(cart: Cart, position: Int) {
        launch(allowMultipleRequest = false) {
            cartRepository.plusToCart(cart)
            cartLiveData.value = cartLiveData.value?.apply {
                get(position).quantity += 1
            }
        }
    }
}