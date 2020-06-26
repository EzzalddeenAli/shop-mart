package com.example.shopmart.data.repository

import android.util.Log
import com.example.shopmart.data.model.Cart
import com.example.shopmart.data.model.Product
import com.example.shopmart.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProductRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ProductRepository {

    private var firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    override suspend fun getProduct(): List<Product> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<Product>> { continuation ->
                val productRef = firebaseFirestore.collection(PRODUCT).get()
                productRef.addOnSuccessListener {
                    val productList = mutableListOf<Product>()
                    for (productSnapshot in it) {
                        productList.add(
                            Product(
                                productSnapshot.id,
                                productSnapshot.data[NAME] as String,
                                productSnapshot.data[PRICE] as Long
                            )
                        )
                    }
                    continuation.resume(productList)
                }
            }
        }
    }

    override suspend fun addProduct(product: HashMap<String, Any>): Boolean {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Boolean> { continuation ->
                firebaseFirestore.collection(PRODUCT)
                    .add(product)
                    .addOnSuccessListener {
                        Log.d(TAG, "added with ID: ${it.id}")
                        continuation.resume(true)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        continuation.resumeWithException(e)
                    }
            }
        }
    }

    override suspend fun addToCart(cart: Cart): Boolean {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<Boolean> { continuation ->
                firebaseUser?.let {
                    firebaseFirestore.collection(
                        String.format("%s/%s/%s", ACCOUNT, it.uid, CART)
                    ).document(cart.productId).set(cart)
                        .addOnSuccessListener {
                            continuation.resume(true)
                        }
                        .addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
                }

            }
        }
    }

    companion object {
        const val TAG = "ProductRepositoryImpl"
    }

}