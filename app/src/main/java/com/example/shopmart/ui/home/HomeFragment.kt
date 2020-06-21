package com.example.shopmart.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.shopmart.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerHome.adapter = HomePagerAdapter(this)
    }
}