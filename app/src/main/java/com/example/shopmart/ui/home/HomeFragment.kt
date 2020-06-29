package com.example.shopmart.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerHome.adapter = HomePagerAdapter(this)

        subscribeUI()

    }

    private fun subscribeUI() {
        with(viewModel) {
            pageLiveData.observe(viewLifecycleOwner, Observer {
                viewPagerHome.currentItem = it
            })
        }
    }
}