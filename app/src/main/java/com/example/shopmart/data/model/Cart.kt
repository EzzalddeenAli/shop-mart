package com.example.shopmart.data.model

import com.google.firebase.firestore.Exclude

data class Cart(
    @get:Exclude
    val productId: String,

    val quantity: Int
)