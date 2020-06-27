package com.example.shopmart.data.repository.cart

import com.example.shopmart.data.model.Cart

interface CartRepository {

    suspend fun addToCart(cart: Cart): Boolean

    suspend fun getCartList(): List<Cart>
}