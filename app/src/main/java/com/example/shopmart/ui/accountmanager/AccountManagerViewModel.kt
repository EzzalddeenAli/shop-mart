package com.example.shopmart.ui.accountmanager

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopmart.util.AccountScreen
import com.example.shopmart.util.AccountScreen.ACCOUNT
import com.example.shopmart.util.AccountScreen.LOGIN
import com.example.shopmart.util.IS_LOGGED_IN

class AccountManagerViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val accountFragmentLiveData = MutableLiveData<AccountScreen>()

    init {
        val isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        accountFragmentLiveData.value =
            if (isLoggedIn) {
                ACCOUNT
            } else {
                LOGIN
            }
    }

    fun updateScreen(accountScreen: AccountScreen) {
        accountFragmentLiveData.value = accountScreen
    }

}