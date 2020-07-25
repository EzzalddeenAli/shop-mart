package ph.mart.home.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import ph.mart.base.BaseViewModel
import ph.mart.home.data.model.Product
import ph.mart.home.data.repository.product.ProductRepository

class ProductViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    var productListLiveData = MutableLiveData<List<Product>>()

    init {
        getData()
    }

    fun getData() {
        launch {
//            productListLiveData.value = productRepository.getProduct()
        }
    }
}