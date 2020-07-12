package ph.mart.shopmart.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import ph.mart.shopmart.data.repository.account.AccountRepository
import ph.mart.shopmart.ui.base.BaseViewModel

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