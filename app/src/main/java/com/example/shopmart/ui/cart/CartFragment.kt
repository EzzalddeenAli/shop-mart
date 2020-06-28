package com.example.shopmart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter = CartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewCart.adapter = cartAdapter

        eventUI()

        subscribeUI()
    }

    private fun eventUI() {
        with(viewModel) {
            swipeRefreshLayoutCart.setOnRefreshListener { getCart() }
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayoutCart.isRefreshing = it
            })

            cartLiveData.observe(viewLifecycleOwner, Observer {
                cartAdapter.setCartList(it)
            })
        }
    }
}