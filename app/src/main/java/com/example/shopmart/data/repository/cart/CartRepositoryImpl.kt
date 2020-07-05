package com.example.shopmart.data.repository.cart

import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.model.Product
import com.example.shopmart.exception.EmptyCart
import com.example.shopmart.exception.NoAccount
import com.example.shopmart.util.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val productReference: CollectionReference
) : CartRepository {

    override suspend fun addToCart(cart: Cart) {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                val cartReference = getCartReference(firebaseFirestore)
                if (cartReference != null) {
                    cartReference.document(cart.productId)
                        .set(cart)
                        .addOnSuccessListener {
                            continuation.resume(Unit)
                        }
                        .addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
                } else {
                    continuation.resumeWithException(NoAccount())
                }
            }
        }
    }

    override suspend fun plusToCart(cart: Cart) {
        updateCart(cart, 1)
    }

    override suspend fun minusToCart(cart: Cart) {
        updateCart(cart, -1)
    }

    private suspend fun updateCart(cart: Cart, value: Long) {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                kotlin.runCatching {
                    getCartReference(firebaseFirestore)!!
                        .document(cart.productId)
                        .update(QUANTITY, FieldValue.increment(value))
                        .addOnSuccessListener { continuation.resume(Unit) }
                        .addOnFailureListener { continuation.resumeWithException(it) }
                }.onFailure {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

    override suspend fun removeToCart(cart: Cart) {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Unit> { continuation ->
                kotlin.runCatching {
                    getCartReference(firebaseFirestore)!!
                        .document(cart.productId)
                        .delete()
                        .addOnSuccessListener { continuation.resume(Unit) }
                        .addOnFailureListener { continuation.resumeWithException(it) }
                }.onFailure {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

    override suspend fun getCartList(): List<Cart> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<Cart>> { continuation ->
                runCatching {
                    getMyCart { cartList ->
                        getProductListByIds(cartList, success = {
                            continuation.resume(cartList)
                        }, fail = {
                            continuation.resumeWithException(it)
                        })
                    }
                }.onFailure {
                    continuation.resumeWithException(it)
                }
            }
        }
    }

    private fun getMyCart(success: (cartList: MutableList<Cart>) -> Unit) {
        val cartReference = getCartReference(firebaseFirestore)
        if (cartReference != null) {
            cartReference.get()
                .addOnSuccessListener { querySnapshot ->
                    val cartList = mutableListOf<Cart>()
                    for (cartSnapshot in querySnapshot) {
                        cartList.add(
                            Cart(
                                cartSnapshot.id,
                                cartSnapshot.data[QUANTITY] as Long
                            )
                        )
                    }
                    success(cartList)
                }
                .addOnFailureListener {
                    throw Exception(it)
                }
        } else {
            throw NoAccount()
        }
    }

    private fun getProductListByIds(
        cartList: MutableList<Cart>,
        success: () -> Unit,
        fail: (exception: Exception) -> Unit
    ) {
        if (cartList.isEmpty()) {
            fail(EmptyCart())
            return
        }

        val productIds = cartList.map { it.productId }
        productReference.whereIn(FieldPath.documentId(), productIds).get()
            .addOnSuccessListener { productSnapshot ->
                for ((i, productDocument) in productSnapshot.withIndex()) {
                    cartList[i].product = Product(
                        productDocument.id,
                        productDocument.data[NAME] as String,
                        productDocument.data[IMAGE] as String,
                        productDocument.data[PRICE] as Long
                    )
                }
                success()
            }
            .addOnFailureListener {
                fail(it)
            }
    }
}