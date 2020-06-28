package com.example.shopmart.ui.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.model.Product
import com.example.shopmart.data.repository.product.ProductRepository
import com.example.shopmart.ui.base.BaseViewModel
import com.example.shopmart.util.NAME
import com.example.shopmart.util.PRICE

class ProductViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    var productListLiveData = MutableLiveData<List<Product>>()

    init {
        getData()
    }

    fun getData() {
        launch {
            productListLiveData.value = productRepository.getProduct()
        }
    }

    fun addProduct() {
        launch {
            productRepository.addProduct(
                hashMapOf(
                    NAME to "Lamp Shade",
                    PRICE to 199
                )
            )
        }
    }

}