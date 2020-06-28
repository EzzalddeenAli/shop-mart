package com.example.shopmart.ui.login

import android.content.Intent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.example.shopmart.Event
import com.example.shopmart.data.repository.account.AccountRepository
import com.example.shopmart.invoke
import com.example.shopmart.ui.base.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class LoginViewModel @ViewModelInject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    val signInSuccess = MutableLiveData<Event<Unit>>()

    private fun signIn(data: Intent?) {
        launch {
            val account =
                GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            accountRepository.firebaseAuthWithGoogle(account?.idToken)
            signInSuccess.invoke()
        }
    }

    fun onActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            signIn(data)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }

}