package com.example.shopmart.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.repository.cart.CartRepository
import com.example.shopmart.ui.base.BaseViewModel

class ProductDetailViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    val showAddedToCartDialog = MutableLiveData<Unit>()

    fun addToCart(productId: String) {
        launch {
            cartRepository.addToCart(Cart(productId, 1))
            showAddedToCartDialog.value = null
        }
    }

}