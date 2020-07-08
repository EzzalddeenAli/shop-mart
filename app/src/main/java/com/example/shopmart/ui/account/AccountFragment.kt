package com.example.shopmart.ui.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.accountmanager.AccountManagerViewModel
import com.example.shopmart.ui.base.BaseFragment
import com.example.shopmart.ui.main.MainViewModel
import com.example.shopmart.util.AccountScreen
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_account.*

@AndroidEntryPoint
class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val viewModel by viewModels<AccountViewModel>()

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogout.setOnClickListener { showLogoutConfirmationDialog() }

        subscribeUI()
    }

    private fun subscribeUI() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, Observer {
                pbLogout.isVisible = it
                buttonLogout.isEnabled = !it
            })

            firebaseUserLiveData.observe(viewLifecycleOwner, Observer {
                tvAccountName.text = it.displayName
                tvAccountUsername.text = it.email
            })
        }
    }

    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.message_logout_confirmation)
            .setPositiveButton(getString(R.string.proceed)) { _, _ ->
                viewModel.logout()
                accountManagerViewModel.updateScreen(AccountScreen.LOGIN)
                mainViewModel.checkAccount()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}