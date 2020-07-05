package com.example.shopmart.data.repository.product

import com.example.shopmart.data.model.Product
import com.example.shopmart.util.IMAGE
import com.example.shopmart.util.NAME
import com.example.shopmart.util.PRICE
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProductRepositoryImpl @Inject constructor(
    private val productReference: CollectionReference
) : ProductRepository {

    override suspend fun getProduct(): List<Product> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<Product>> { continuation ->
                productReference.get()
                    .addOnSuccessListener {
                        val productList = mutableListOf<Product>()
                        for (productSnapshot in it) {
                            productList.add(
                                Product(
                                    productSnapshot.id,
                                    productSnapshot.data[NAME] as String,
                                    productSnapshot.data[IMAGE] as String,
                                    productSnapshot.data[PRICE] as Long
                                )
                            )
                        }
                        continuation.resume(productList)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }
    }
}