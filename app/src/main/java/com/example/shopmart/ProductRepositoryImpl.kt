package com.example.shopmart

import android.util.Log
import com.example.shopmart.ProductViewModel.Companion.PRODUCT
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@ExperimentalCoroutinesApi
object ProductRepositoryImpl: ProductRepository {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getProduct(): List<Product> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<Product>> { continuation ->
                val productRef = firebaseFirestore.collection(PRODUCT).get()
                productRef.addOnSuccessListener {
                    val productList = mutableListOf<Product>()
                    for (productSnapshot in it) {
                        productList.add(
                            Product(
                                productSnapshot.data[ProductViewModel.NAME] as String,
                                productSnapshot.data[ProductViewModel.PRICE] as Long
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

    const val TAG = "ProductRepositoryImpl"

}