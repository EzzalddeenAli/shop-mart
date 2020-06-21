package com.example.shopmart.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmart.R
import com.example.shopmart.data.model.Product
import com.example.shopmart.ui.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_product.view.*

class ProductViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productList = emptyList<Product>()

    fun setProduct(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(
            view
        )
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = productList[position]
        holder.itemView.apply {
            tvName.text = product.name
            tvPrice.text = product.price.toString()

            setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.homeToProductDetail(product)
                )
            }
        }
    }
}