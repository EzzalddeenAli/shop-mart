package com.example.shopmart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProductViewModel : ViewModel() {

    private val productRepository = ProductRepositoryImpl

    var productListLiveData = MutableLiveData<List<Product>>()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            productListLiveData.value = productRepository.getProduct()
        }
    }

    fun addProduct() {
        viewModelScope.launch {
            val isAddedSuccessFully = productRepository.addProduct(
                hashMapOf(
                    NAME to "Lamp Shade",
                    PRICE to 199
                )
            )

            if (isAddedSuccessFully) {
                getData()
            }
        }
    }

    companion object {
        const val PRODUCT = "product"
        const val NAME = "name"
        const val PRICE = "price"
    }

}