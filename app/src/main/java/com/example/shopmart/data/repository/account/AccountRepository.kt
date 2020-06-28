package com.example.shopmart.data.repository.account

import com.google.firebase.auth.FirebaseUser

interface AccountRepository {

    suspend fun firebaseAuthWithGoogle(idToken: String?)

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

    fun isLoggedIn(): Boolean
}