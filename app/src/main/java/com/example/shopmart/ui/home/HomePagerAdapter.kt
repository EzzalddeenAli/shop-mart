package com.example.shopmart.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shopmart.ui.account.AccountFragment
import com.example.shopmart.ui.home.product.ProductFragment
import com.example.shopmart.ui.login.LoginFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    private val isLoggedIn = false

    @ExperimentalCoroutinesApi
    override fun createFragment(position: Int) = when (position) {
        0 -> ProductFragment()
        1 -> {
            if (isLoggedIn) {
                AccountFragment()
            } else {
                LoginFragment()
            }
        }
        else -> throw Exception("HomePagerAdapter Invalid Position")
    }
}
