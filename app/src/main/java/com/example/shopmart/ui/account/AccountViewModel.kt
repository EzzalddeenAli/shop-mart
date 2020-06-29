package com.example.shopmart.ui.account

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.repository.account.AccountRepository
import com.example.shopmart.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseUser

class AccountViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    val firebaseUserLiveData = MutableLiveData<FirebaseUser>()

    init {
        val currentUser = accountRepository.getCurrentUser()
        if (currentUser != null) {
            firebaseUserLiveData.value = currentUser
        }
    }

    fun logout() {
        accountRepository.signOut()
    }

}