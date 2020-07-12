package ph.mart.shopmart.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import ph.mart.shopmart.data.model.Cart
import ph.mart.shopmart.data.repository.cart.CartRepository
import ph.mart.shopmart.ui.base.BaseViewModel

class ProductDetailViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    fun addToCart(productId: String) {
        launch(ProductDetailEvent.AddedToCart) {
            cartRepository.addToCart(Cart(productId, 1))
        }
    }

    internal sealed class ProductDetailEvent: BaseEvent() {
        object AddedToCart: ProductDetailEvent()
    }

}