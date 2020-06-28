package com.example.shopmart.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

fun getFirebaseAuth(): FirebaseAuth =
    FirebaseAuth.getInstance()

fun getFirebaseUser(): FirebaseUser? =
    getFirebaseAuth().currentUser

fun getCartReference(
    firebaseFirestore: FirebaseFirestore
): CollectionReference? =
    getFirebaseUser()?.let {
        firebaseFirestore.collection(
            String.format("%s/%s/%s", ACCOUNT, it.uid, CART)
        )
    }