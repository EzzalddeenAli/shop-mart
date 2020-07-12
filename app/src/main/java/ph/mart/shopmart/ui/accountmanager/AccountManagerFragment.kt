package ph.mart.shopmart.ui.accountmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import ph.mart.shopmart.R
import ph.mart.shopmart.ui.account.AccountFragment
import ph.mart.shopmart.ui.base.BaseFragment
import ph.mart.shopmart.ui.login.LoginFragment
import ph.mart.shopmart.ui.signup.SignupFragment
import ph.mart.shopmart.util.AccountScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountManagerFragment : BaseFragment(R.layout.fragment_account_manager) {

    private val viewModel by activityViewModels<AccountManagerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.accountFragmentLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                AccountScreen.LOGIN -> replaceFragment(LoginFragment())
                AccountScreen.SIGNUP -> replaceFragment(SignupFragment())
                AccountScreen.ACCOUNT -> replaceFragment(AccountFragment())
                else -> Unit
            }
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.fcvAccount, fragment)
        }
    }

}