package ph.mart.shopmart.ui.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import ph.mart.shopmart.data.model.Product
import ph.mart.shopmart.data.repository.product.ProductRepository
import ph.mart.shopmart.ui.base.BaseViewModel

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
}