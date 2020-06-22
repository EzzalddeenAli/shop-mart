package com.example.shopmart.ui.account

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.Event
import com.example.shopmart.ui.base.BaseViewModel
import com.example.shopmart.util.IS_LOGGED_IN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()

    val firebaseUserLiveData = MutableLiveData<FirebaseUser>()

    val navigateToLogin = MutableLiveData<Event<Unit>>()

    init {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            firebaseUserLiveData.value = currentUser
        } else {
            navigateToLogin.value = null
        }
    }

    fun logout() {
        loadingLiveData.value = true
        firebaseAuth.signOut()
        with(sharedPreferences.edit()) {
            remove(IS_LOGGED_IN)
            apply()
        }
        loadingLiveData.value = false
    }

}