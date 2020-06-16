package com.example.shopmart.data.repository

import com.example.shopmart.data.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface ProductRepository {

    suspend fun getProduct(): List<Product>

    suspend fun addProduct(product: HashMap<String, Any>): Boolean

}