package com.example.shopmart.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shopmart.R
import com.example.shopmart.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product.*

@AndroidEntryPoint
class ProductFragment : BaseFragment(R.layout.fragment_product) {

    private val viewModel by viewModels<ProductViewModel>()

    private val productViewAdapter = ProductViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewProduct.adapter = productViewAdapter

        buttonAddProduct.setOnClickListener {
            viewModel.addProduct()
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayoutProduct.isRefreshing = it
            })

            productListLiveData.observe(viewLifecycleOwner, Observer {
                productViewAdapter.setProduct(it)
            })
        }
    }
}