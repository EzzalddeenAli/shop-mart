package com.example.shopmart.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.repository.account.AccountRepository
import com.example.shopmart.ui.base.BaseViewModel

class SignupViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    val signupSuccessLiveData = MutableLiveData<Unit>()

    fun createAccount(email: String, password: String) {
        launch {
            accountRepository.createAccount(email, password)
            signupSuccessLiveData.value = Unit
        }
    }
}