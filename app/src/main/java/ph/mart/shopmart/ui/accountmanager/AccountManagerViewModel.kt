package ph.mart.shopmart.ui.accountmanager

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import ph.mart.shopmart.data.repository.account.AccountRepository
import ph.mart.shopmart.ui.base.BaseViewModel
import ph.mart.shopmart.util.AccountScreen
import ph.mart.shopmart.util.AccountScreen.ACCOUNT
import ph.mart.shopmart.util.AccountScreen.LOGIN

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