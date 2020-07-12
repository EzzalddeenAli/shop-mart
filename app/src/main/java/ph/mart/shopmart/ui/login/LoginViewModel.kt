package ph.mart.shopmart.ui.login

import android.content.Intent
import androidx.hilt.lifecycle.ViewModelInject
import ph.mart.shopmart.data.repository.account.AccountRepository
import ph.mart.shopmart.ui.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class LoginViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private fun signIn(data: Intent?) {
        launch(LoginEvent.LoginSuccess) {
            val account =
                GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            accountRepository.firebaseAuthWithGoogle(account?.idToken)
        }
    }

    fun onActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            signIn(data)
        }
    }

    fun loginAccount(email: String, password: String) {
        launch(LoginEvent.LoginSuccess) {
            accountRepository.loginAccount(email, password)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }

    internal sealed class LoginEvent: BaseEvent() {
        object LoginSuccess : LoginEvent()
    }

}