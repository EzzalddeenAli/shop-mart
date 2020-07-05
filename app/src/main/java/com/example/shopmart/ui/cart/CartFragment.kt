package com.example.shopmart.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.shopmart.R
import com.example.shopmart.data.model.Cart
import com.example.shopmart.databinding.FragmentCartBinding
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
class CartFragment : BaseFragment() {

    private val viewModel by viewModels<CartViewModel>()

    private lateinit var cartAdapter: CartAdapter

    private lateinit var viewDataBinding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCartBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventUI()

        subscribeUI()

        setupListAdapter()
    }

    private fun setupListAdapter() {
        cartAdapter = CartAdapter(viewModel)
        viewDataBinding.recyclerViewCart.adapter = cartAdapter
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

            confirmRemoveToCartLiveData.observe(viewLifecycleOwner, Observer {
                showRemoveToCartDialog(it)
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

    private fun showRemoveToCartDialog(cart: Cart) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.message_remove_to_cart)
            .setPositiveButton(getString(R.string.proceed)) { _, _ ->
                viewModel.removeToCart(cart)
            }
            .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}