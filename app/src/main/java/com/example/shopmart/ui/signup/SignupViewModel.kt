package com.example.shopmart.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import com.example.shopmart.data.repository.account.AccountRepository
import com.example.shopmart.ui.base.BaseViewModel

internal class SignupViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    fun createAccount(email: String, password: String) {
        launch(SignupEvent.SignupSuccess) {
            accountRepository.createAccount(email, password)
        }
    }

    internal sealed class SignupEvent : BaseEvent() {
        object SignupSuccess : SignupEvent()
    }
}