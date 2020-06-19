package com.example.shopmart.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shopmart.home.account.AccountFragment
import com.example.shopmart.home.product.ProductFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    @ExperimentalCoroutinesApi
    override fun createFragment(position: Int) = when (position) {
        0 -> ProductFragment()
        1 -> AccountFragment()
        else -> throw Exception("HomePagerAdapter Invalid Position")
    }
}
