package com.example.shopmart.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmart.Product
import com.example.shopmart.ProductRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProductViewModel : ViewModel() {

    var productListLiveData = MutableLiveData<List<Product>>()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            productListLiveData.value = ProductRepositoryImpl.getProduct()
        }
    }

    fun addProduct() {
        viewModelScope.launch {
            val isAddedSuccessFully =
                ProductRepositoryImpl.addProduct(
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