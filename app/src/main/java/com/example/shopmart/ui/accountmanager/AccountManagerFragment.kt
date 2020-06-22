package com.example.shopmart.ui.accountmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.base.BaseFragment
import com.example.shopmart.ui.account.AccountFragment
import com.example.shopmart.ui.login.LoginFragment
import com.example.shopmart.util.AccountScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountManagerFragment : BaseFragment(R.layout.fragment_account_manager) {

    private val viewModel by activityViewModels<AccountManagerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.accountFragmentLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                AccountScreen.LOGIN -> replaceFragment(LoginFragment())
                AccountScreen.SIGNUP -> TODO()
                AccountScreen.ACCOUNT -> replaceFragment(AccountFragment())
            }
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.fcvAccount, fragment)
        }
    }

}