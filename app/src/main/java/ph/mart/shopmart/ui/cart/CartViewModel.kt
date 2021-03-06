package ph.mart.shopmart.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import ph.mart.shopmart.Event
import ph.mart.shopmart.data.model.Cart
import ph.mart.shopmart.data.repository.cart.CartRepository
import ph.mart.shopmart.exception.EmptyCart
import ph.mart.shopmart.ui.base.BaseViewModel

class CartViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    val cartLiveData = MutableLiveData<MutableList<Cart>>()

    val confirmRemoveToCartLiveData = MutableLiveData<Event<Pair<Int, Cart>>>()

    val totalLiveData = MutableLiveData<Long>()

    init {
        getCart()
    }

    fun getCart() {
        launch {
            cartLiveData.value = cartRepository.getCartList().toMutableList()
            totalLiveData.value = getTotal()
        }
    }

    fun minusToCart(cart: Cart, position: Int) {
        if (cart.quantity <= 1) {
            confirmRemoveToCartLiveData.value = Event(position to cart)
        } else {
            launch(allowMultipleRequest = false) {
                cartRepository.minusToCart(cart)
                updateCartList(CartListAction.MINUS, position)
            }
        }
    }

    fun removeToCart(cart: Cart, position: Int) {
        launch(allowMultipleRequest = false) {
            cartRepository.removeToCart(cart)
            updateCartList(CartListAction.REMOVE, position)
            checkCartList()
        }
    }

    private fun checkCartList() {
        if (cartLiveData.value.isNullOrEmpty()) {
            errorLiveData.value = Event(EmptyCart())
        }
    }

    fun addToCart(cart: Cart, position: Int) {
        launch(allowMultipleRequest = false) {
            cartRepository.plusToCart(cart)
            updateCartList(CartListAction.ADD, position)
        }
    }

    private fun updateCartList(cartListAction: CartListAction, position: Int) {
        cartLiveData.value = cartLiveData.value?.apply {
            when (cartListAction) {
                CartListAction.ADD -> get(position).quantity += 1
                CartListAction.MINUS -> get(position).quantity -= 1
                CartListAction.REMOVE -> removeAt(position)
            }
        }

        totalLiveData.value = getTotal()
    }

    private fun getTotal(): Long {
        var total = 0L
        cartLiveData.value?.iterator()?.forEach {
            val price = it.product?.price ?: 0
            total += price * it.quantity
        }
        return total
    }

    enum class CartListAction {
        ADD,
        MINUS,
        REMOVE
    }
}