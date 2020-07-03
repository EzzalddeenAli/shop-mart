package com.example.shopmart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.shopmart.R
import com.example.shopmart.exception.EmptyCart
import com.example.shopmart.exception.NoAccount
import com.example.shopmart.ui.base.BaseFragment
import com.example.shopmart.util.setupSnackbar
import com.example.shopmart.util.showEmptyCartView
import com.example.shopmart.util.showNoAccountView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter = CartAdapter(remove = { cart, position ->
        viewModel.removeToCart(cart, position)
    }, add = { cart, position ->
        viewModel.addToCart(cart, position)
    })

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
            view?.setupSnackbar(viewLifecycleOwner, snackBarLiveData, Snackbar.LENGTH_SHORT)

            loadingLiveData.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayoutCart.isRefreshing = it
            })

            cartLiveData.observe(viewLifecycleOwner, Observer {
                cartAdapter.submitList(it)
            })

            errorLiveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NoAccount -> {
                        cartAdapter.submitList(mutableListOf())
                        clCartParent.showNoAccountView { getCart() }
                    }
                    is EmptyCart -> {
                        clCartParent.showEmptyCartView {
                            findNavController().navigate(CartFragmentDirections.cartToProduct())
                        }
                    }
                    else -> {
                        Timber.e("not handle error ${it.message}")
                    }
                }
            })

        }
    }
}