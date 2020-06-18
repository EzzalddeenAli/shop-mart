package com.example.shopmart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.shopmart.data.model.Product
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

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