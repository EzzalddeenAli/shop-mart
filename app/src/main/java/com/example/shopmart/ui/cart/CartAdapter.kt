package com.example.shopmart.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmart.R
import com.example.shopmart.data.model.Cart
import kotlinx.android.synthetic.main.item_cart.view.*

class CartAdapter(
    private val remove: (cart: Cart, position: Int) -> Unit,
    private val add: (cart: Cart, position: Int) -> Unit
) : ListAdapter<Cart, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(cart: Cart) {
            itemView.apply {
                tvProductName?.text = cart.product?.name
                tvProductQuantity?.text = cart.quantity.toString()

                buttonRemove.setOnClickListener { remove(cart, position) }

                buttonAdd.setOnClickListener { add(cart, position) }
            }
        }
    }

    override fun submitList(list: List<Cart>?) {
        super.submitList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bindTo(cart)
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<Cart>() {
    override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
        return oldItem.quantity == newItem.quantity
    }

    override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
        return oldItem == newItem
    }
}