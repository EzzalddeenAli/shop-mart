package com.example.shopmart.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.accountmanager.AccountManagerViewModel
import com.example.shopmart.util.AccountScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    @Inject
    lateinit var signInIntent: Intent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin.setOnClickListener {
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        viewModel.signInSuccess.observe(viewLifecycleOwner, Observer {
            accountManagerViewModel.updateScreen(AccountScreen.ACCOUNT)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            viewModel.signIn(data)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }

}