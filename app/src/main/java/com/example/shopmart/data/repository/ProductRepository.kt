package com.example.shopmart.data.repository

import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.model.Product

interface ProductRepository {

    suspend fun getProduct(): List<Product>

    suspend fun addProduct(product: HashMap<String, Any>): Boolean

    suspend fun addToCart(cart: Cart): Boolean

}