package com.example.shopmart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

// UserProfileViewModel
class ProductViewModel : ViewModel() {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    var productListLiveData = MutableLiveData<List<Product>>()

    init {
        getData()
    }

    private fun getData() {
        val productRef = firebaseFirestore.collection(PRODUCT).get()
        productRef.addOnSuccessListener {
            val productList = mutableListOf<Product>()
            for (productSnapshot in it) {
                productList.add(
                    Product(
                        productSnapshot.data[NAME] as String,
                        productSnapshot.data[PRICE] as Long
                    )
                )
            }
            productListLiveData.value = productList
        }
    }

    fun addProduct() {
        val product = hashMapOf(
            NAME to "Qwe",
            PRICE to 300
        )

        firebaseFirestore.collection(PRODUCT)
            .add(product)
            .addOnSuccessListener {
                Log.d(TAG, "added with ID: ${it.id}")
                getData()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    companion object {
        const val TAG = "MainActivity"
        const val PRODUCT = "product"
        const val NAME = "name"
        const val PRICE = "price"
    }

}