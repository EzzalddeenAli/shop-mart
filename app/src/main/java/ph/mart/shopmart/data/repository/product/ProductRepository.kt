package ph.mart.shopmart.data.repository.product

import ph.mart.shopmart.data.model.Product

interface ProductRepository {

    suspend fun getProduct(): List<Product>
}