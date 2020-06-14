package com.example.shopmart.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.shopmart.R
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProductFragment: Fragment(R.layout.fragment_product) {

    private val viewModel = ProductViewModel()

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
        viewModel.productListLiveData.observe(viewLifecycleOwner, Observer {
            productViewAdapter.setProduct(it)
        })
    }
}