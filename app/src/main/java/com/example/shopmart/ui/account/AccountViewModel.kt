package com.example.shopmart.ui.account

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.shopmart.util.IS_LOGGED_IN

class AccountViewModel@ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    fun logout() {
        with(sharedPreferences.edit()) {
            remove(IS_LOGGED_IN)
            apply()
        }
    }

}