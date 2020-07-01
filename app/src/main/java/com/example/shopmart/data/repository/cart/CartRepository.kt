package com.example.shopmart.data.repository.cart

import com.example.shopmart.data.model.Cart

interface CartRepository {

    suspend fun addToCart(cart: Cart)

    suspend fun plusToCart(cart: Cart)

    suspend fun minusToCart(cart: Cart)

    suspend fun removeToCart(cart: Cart)

    suspend fun getCartList(): List<Cart>
}