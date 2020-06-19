package com.example.shopmart.ui.home.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmart.data.model.Product
import com.example.shopmart.data.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProductViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

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
            val isAddedSuccessFully =
                productRepository.addProduct(
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