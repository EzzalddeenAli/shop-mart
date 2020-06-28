package com.example.shopmart.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.accountmanager.AccountManagerViewModel
import com.example.shopmart.ui.base.BaseFragment
import com.example.shopmart.ui.login.LoginViewModel.Companion.RC_SIGN_IN
import com.example.shopmart.util.AccountScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    @Inject
    lateinit var signInIntent: Intent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventUI()

        subscribeUI()
    }

    private fun eventUI() {
        buttonLogin.setOnClickListener {
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            signInSuccess.observe(viewLifecycleOwner, Observer {
                accountManagerViewModel.updateScreen(AccountScreen.ACCOUNT)
            })

            loadingLiveData.observe(viewLifecycleOwner, Observer {
                pbLogin.isVisible = it
                buttonLogin.isEnabled = !it
                etUsername.isEnabled = !it
                etPassword.isEnabled = !it
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, data)
    }
}