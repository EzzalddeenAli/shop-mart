package com.example.shopmart.ui.account

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
import kotlinx.android.synthetic.main.fragment_account.*

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private val viewModel by viewModels<AccountViewModel>()

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogout.setOnClickListener {
            viewModel.logout()
            accountManagerViewModel.updateScreen(AccountScreen.LOGIN)
        }

        viewModel.firebaseUserLiveData.observe(viewLifecycleOwner, Observer {
            tvAccountName.text = it.displayName
            tvAccountUsername.text = it.email
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            viewModel.logout()
            accountManagerViewModel.updateScreen(AccountScreen.LOGIN)
        })
    }
}