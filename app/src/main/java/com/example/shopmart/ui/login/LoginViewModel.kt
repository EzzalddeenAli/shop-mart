package com.example.shopmart.ui.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopmart.Event
import com.example.shopmart.util.IS_LOGGED_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val signInSuccess = MutableLiveData<Event<Unit>>()

    fun signIn(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Timber.d("Google sign in failed")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // TODO: show loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("signInWithCredential:success")
                    signInSuccess()
                } else {
                    Timber.d("signInWithCredential:failure ${task.exception}")
                }
            }
    }

    private fun signInSuccess() {
        with(sharedPreferences.edit()) {
            putBoolean(IS_LOGGED_IN, true)
            apply()
        }
        signInSuccess.value = null
    }

}