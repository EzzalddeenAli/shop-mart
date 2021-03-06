package ph.mart.shopmart.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ph.mart.shopmart.EventObserver
import ph.mart.shopmart.R
import ph.mart.shopmart.ui.accountmanager.AccountManagerViewModel
import ph.mart.shopmart.ui.base.BaseFragment
import ph.mart.shopmart.ui.login.LoginViewModel.Companion.RC_SIGN_IN
import ph.mart.shopmart.ui.login.LoginViewModel.LoginEvent.LoginSuccess
import ph.mart.shopmart.ui.main.MainViewModel
import ph.mart.shopmart.util.AccountScreen
import ph.mart.shopmart.util.setupSnackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    private val mainViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var signInIntent: Intent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventUI()

        subscribeUI()
    }

    private fun eventUI() {
        buttonLogin.setOnClickListener {
            viewModel.loginAccount(etEmail.text.toString(), etPassword.text.toString())
        }

        etEmail.addTextChangedListener {
            checkLoginStatus()
        }

        etPassword.addTextChangedListener {
            checkLoginStatus()
        }

        buttonContinueWithGoogle.setOnClickListener {
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        buttonCreateAccount.setOnClickListener {
            accountManagerViewModel.updateScreen(AccountScreen.SIGNUP)
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            view?.setupSnackbar(viewLifecycleOwner, snackBarLiveData, Snackbar.LENGTH_SHORT)

            baseEventLiveData.observe(viewLifecycleOwner, EventObserver {
                when (it) {
                    is LoginSuccess -> {
                        showSuccessfullyLoginDialog()
                        accountManagerViewModel.updateScreen(AccountScreen.ACCOUNT)
                        mainViewModel.checkAccount()
                    }
                }
            })

            errorLiveData.observe(viewLifecycleOwner, EventObserver {
                Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
            })

            loadingLiveData.observe(viewLifecycleOwner, Observer {
                pbLogin.isVisible = it
                buttonLogin.isEnabled = !it
                etEmail.isEnabled = !it
                etPassword.isEnabled = !it
                buttonContinueWithGoogle.isEnabled = !it
                buttonCreateAccount.isEnabled = !it
            })
        }
    }

    private fun checkLoginStatus() {
        buttonLogin.isEnabled =
            etEmail.text.toString().isNotBlank() and etPassword.text.toString().isNotBlank()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, data)
    }

    private fun showSuccessfullyLoginDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.successfully_login)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }
}