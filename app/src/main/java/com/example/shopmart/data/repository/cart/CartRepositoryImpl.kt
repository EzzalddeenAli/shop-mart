package com.example.shopmart.data.repository.cart

import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.model.Product
import com.example.shopmart.util.NAME
import com.example.shopmart.util.PRICE
import com.example.shopmart.util.QUANTITY
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor(
    private val myCartReference: CollectionReference?,
    private val productReference: CollectionReference
) : CartRepository {

    override suspend fun addToCart(cart: Cart): Boolean {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Boolean> { continuation ->
                myCartReference
                    ?.document(cart.productId)
                    ?.set(cart)
                    ?.addOnSuccessListener {
                        continuation.resume(true)
                    }
                    ?.addOnFailureListener { e ->
                        continuation.resumeWithException(e)
                    }
            }
        }
    }

    override suspend fun getCartList(): List<Cart> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<Cart>> { continuation ->
                getMyCart { cartList ->
                    getProductListByIds(cartList) {
                        continuation.resume(cartList)
                    }
                }
            }
        }
    }

    private fun getMyCart(function: (cartList: MutableList<Cart>) -> Unit) {
        myCartReference
            ?.get()
            ?.addOnSuccessListener { querySnapshot ->
                val cartList = mutableListOf<Cart>()
                for (cartSnapshot in querySnapshot) {
                    cartList.add(
                        Cart(
                            cartSnapshot.id,
                            cartSnapshot.data[QUANTITY] as Long
                        )
                    )
                }
                function(cartList)
            }
            ?.addOnFailureListener {
                Timber.e(it)
            }
    }

    private fun getProductListByIds(
        cartList: MutableList<Cart>,
        done: () -> Unit
    ) {
        val productIds = cartList.map { it.productId }

        if (productIds.isEmpty()) {
            done()
            return
        }

        productReference.whereIn(FieldPath.documentId(), productIds).get()
            .addOnSuccessListener { productSnapshot ->
                for ((i, productDocument) in productSnapshot.withIndex()) {
                    cartList[i].product = Product(
                        productDocument.id,
                        productDocument.data[NAME] as String,
                        productDocument.data[PRICE] as Long
                    )
                }
                done()
            }
            .addOnFailureListener {
                Timber.e(it)
            }
    }


}