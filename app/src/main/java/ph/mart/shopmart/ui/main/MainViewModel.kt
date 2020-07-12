package ph.mart.shopmart.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import ph.mart.shopmart.data.repository.account.AccountRepository
import ph.mart.shopmart.ui.base.BaseViewModel

class MainViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {
    val isLoggedInLiveData = MutableLiveData<Boolean>()

    init {
        checkAccount()
    }

    fun checkAccount() {
        isLoggedInLiveData.value = accountRepository.getCurrentUser() != null
    }

}