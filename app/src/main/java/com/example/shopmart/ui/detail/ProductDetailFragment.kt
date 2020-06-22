package com.example.shopmart.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.shopmart.R
import com.example.shopmart.data.model.Product
import com.example.shopmart.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {

    private val args: ProductDetailFragmentArgs by navArgs()

    private val product: Product by lazy { args.product }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    private fun setData() {
        tvProductName.text = product.name
        tvProductPrice.text = product.price.toString()
    }
}