package ph.mart.shopmart.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ph.mart.shopmart.EventObserver
import ph.mart.shopmart.R
import ph.mart.shopmart.data.model.Cart
import ph.mart.shopmart.databinding.FragmentCartBinding
import ph.mart.shopmart.exception.EmptyCart
import ph.mart.shopmart.exception.NoAccount
import ph.mart.shopmart.ui.base.BaseFragment
import ph.mart.shopmart.util.setupSnackbar
import ph.mart.shopmart.util.showEmptyCartView
import ph.mart.shopmart.util.showNoAccountView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter by lazy { CartAdapter(viewModel) }

    private lateinit var viewDataBinding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCartBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

            confirmRemoveToCartLiveData.observe(viewLifecycleOwner, EventObserver {
                showRemoveToCartDialog(it)
            })

            errorLiveData.observe(viewLifecycleOwner, EventObserver {
                when (it) {
                    is NoAccount -> {
                        cartAdapter.submitList(mutableListOf())
                        clCartParent.showNoAccountView { getCart() }
                    }
                    is EmptyCart -> {
                        clCartParent.showEmptyCartView {
//                            findNavController().navigate(CartFragmentDirections.cartToProduct())
                        }
                    }
                    else -> {
                        Timber.e("not handle error ${it.message}")
                    }
                }
            })

        }
    }

    private fun showRemoveToCartDialog(data: Pair<Int, Cart>) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.message_remove_to_cart)
            .setPositiveButton(getString(R.string.proceed)) { _, _ ->
                viewModel.removeToCart(data.second, data.first)
            }
            .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}