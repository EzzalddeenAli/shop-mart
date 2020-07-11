package com.example.shopmart.ui.signup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.EventObserver
import com.example.shopmart.R
import com.example.shopmart.ui.accountmanager.AccountManagerViewModel
import com.example.shopmart.ui.base.BaseFragment
import com.example.shopmart.util.AccountScreen
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_signup.*

@AndroidEntryPoint
class SignupFragment : BaseFragment(R.layout.fragment_signup) {

    private val viewModel by viewModels<SignupViewModel>()

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventUI()

        subscribeUI()
    }

    private fun eventUI() {
        buttonSignup.setOnClickListener {
            viewModel.createAccount(etEmail.text.toString(), etPassword.text.toString())
        }

        etEmail.addTextChangedListener {
            checkSignupStatus()
        }

        etPassword.addTextChangedListener {
            checkSignupStatus()
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, Observer {
                pbSignup.isVisible = it
                buttonSignup.isEnabled = !it
            })

            errorLiveData.observe(viewLifecycleOwner, EventObserver {
                when (it) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        showErrorDialog(it.message)
                    }
                }
            })

            signupSuccessLiveData.observe(viewLifecycleOwner, Observer {
                showAccountCreatedDialog()
                accountManagerViewModel.updateScreen(AccountScreen.LOGIN)
            })
        }
    }

    private fun checkSignupStatus() {
        buttonSignup.isEnabled =
            etEmail.text.toString().isNotBlank() and etPassword.text.toString().isNotBlank()
    }

    private fun showErrorDialog(message: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }


    private fun showAccountCreatedDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.message_account_created)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }

}