package ph.mart.shopmart.data.repository.cart

import ph.mart.shopmart.data.model.Cart

interface CartRepository {

    suspend fun addToCart(cart: Cart)

    suspend fun plusToCart(cart: Cart)

    suspend fun minusToCart(cart: Cart)

    suspend fun removeToCart(cart: Cart)

    suspend fun getCartList(): List<Cart>
}