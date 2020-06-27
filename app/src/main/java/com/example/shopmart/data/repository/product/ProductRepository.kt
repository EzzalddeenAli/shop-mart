package com.example.shopmart.data.repository.product

import com.example.shopmart.data.model.Product

interface ProductRepository {

    suspend fun getProduct(): List<Product>

    suspend fun addProduct(product: HashMap<String, Any>): Boolean

}