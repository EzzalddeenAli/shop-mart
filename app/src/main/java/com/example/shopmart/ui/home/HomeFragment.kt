package com.example.shopmart.ui.home

import android.os.Bundle
import android.view.View
import com.example.shopmart.R
import com.example.shopmart.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerHome.adapter = HomePagerAdapter(this)
    }
}