package com.example.shopmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel = ProductViewModel()

    private val productViewAdapter = ProductViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewProduct.adapter = productViewAdapter

        buttonAddProduct.setOnClickListener {
            viewModel.addProduct()
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.productListLiveData.observe(this, Observer {
                productViewAdapter.setProduct(it)
        })
    }

}