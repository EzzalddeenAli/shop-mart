package com.example.shopmart.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shopmart.ui.accountmanager.AccountManagerFragment
import com.example.shopmart.ui.cart.CartFragment
import com.example.shopmart.ui.product.ProductFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> ProductFragment()
        1 -> CartFragment()
        2 -> AccountManagerFragment()
        else -> throw Exception("HomePagerAdapter Invalid Position")
    }
}
