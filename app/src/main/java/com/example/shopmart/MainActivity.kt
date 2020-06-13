package com.example.shopmart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val productViewAdapter = ProductViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewProduct.adapter = productViewAdapter

        getData()

        buttonAddProduct.setOnClickListener {
            addProduct()
        }
    }

    private fun addProduct() {
        val product = hashMapOf(
            NAME to "Computer",
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
            productViewAdapter.setProduct(productList)
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val PRODUCT = "product"
        const val NAME = "name"
        const val PRICE = "price"
    }

}