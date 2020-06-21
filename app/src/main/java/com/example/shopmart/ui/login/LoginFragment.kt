package com.example.shopmart.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.shopmart.util.IS_LOGGED_IN
import com.example.shopmart.R
import com.example.shopmart.ui.accountmanager.AccountManagerViewModel
import com.example.shopmart.util.AccountScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private val accountManagerViewModel by activityViewModels<AccountManagerViewModel>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin.setOnClickListener {
            with(sharedPreferences.edit()) {
                putBoolean(IS_LOGGED_IN, true)
                apply()
            }

            accountManagerViewModel.updateScreen(AccountScreen.ACCOUNT)
        }
    }

}