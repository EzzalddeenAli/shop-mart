package com.example.shopmart.ui.accountmanager

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopmart.util.AccountScreen
import com.example.shopmart.util.AccountScreen.ACCOUNT
import com.example.shopmart.util.AccountScreen.LOGIN
import com.example.shopmart.util.IS_LOGGED_IN
import com.google.firebase.auth.FirebaseAuth

class AccountManagerViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val accountFragmentLiveData = MutableLiveData<AccountScreen>()

    init {
        checkAccount()
    }

    private fun checkAccount() {
        val isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        accountFragmentLiveData.value =
            if (firebaseAuth.currentUser != null && isLoggedIn) {
                ACCOUNT
            } else {
                firebaseAuth.signOut()
                LOGIN
            }
    }

    fun updateScreen(accountScreen: AccountScreen) {
        accountFragmentLiveData.value = accountScreen
    }

}