package com.example.shopmart.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.repository.ProductRepository
import com.example.shopmart.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ProductDetailViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    var loadingLiveData = MutableLiveData<Boolean>()

    fun addToCart(productId: String) {
        viewModelScope.launch {
            loadingLiveData.value = true
            productRepository.addToCart(Cart(productId, 1))
            loadingLiveData.value = false
        }
    }

}