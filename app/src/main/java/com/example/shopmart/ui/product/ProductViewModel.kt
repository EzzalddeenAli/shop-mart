package com.example.shopmart.ui.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopmart.data.model.Product
import com.example.shopmart.data.repository.product.ProductRepository
import com.example.shopmart.ui.base.BaseViewModel
import com.example.shopmart.util.NAME
import com.example.shopmart.util.PRICE
import kotlinx.coroutines.launch

class ProductViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    var productListLiveData = MutableLiveData<List<Product>>()

    var loadingLiveData = MutableLiveData<Boolean>()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            loadingLiveData.value = true
            productListLiveData.value = productRepository.getProduct()
            loadingLiveData.value = false
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

}