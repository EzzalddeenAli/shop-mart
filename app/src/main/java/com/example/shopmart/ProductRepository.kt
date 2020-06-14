package com.example.shopmart

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface ProductRepository {

    suspend fun getProduct(): List<Product>

    suspend fun addProduct(product: HashMap<String, Any>): Boolean

}