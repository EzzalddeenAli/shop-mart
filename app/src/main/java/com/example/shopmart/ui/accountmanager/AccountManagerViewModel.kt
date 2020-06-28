package com.example.shopmart.ui.accountmanager

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.data.repository.account.AccountRepository
import com.example.shopmart.ui.base.BaseViewModel
import com.example.shopmart.util.AccountScreen
import com.example.shopmart.util.AccountScreen.ACCOUNT
import com.example.shopmart.util.AccountScreen.LOGIN

class AccountManagerViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    val accountFragmentLiveData = MutableLiveData<AccountScreen>()

    init {
        checkAccount()
    }

    private fun checkAccount() {
        accountFragmentLiveData.value =
            if (accountRepository.isLoggedIn()) {
                ACCOUNT
            } else {
                LOGIN
            }
    }

    fun updateScreen(accountScreen: AccountScreen) {
        accountFragmentLiveData.value = accountScreen
    }

}