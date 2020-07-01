package com.example.shopmart.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmart.R
import com.example.shopmart.data.model.Cart
import kotlinx.android.synthetic.main.item_cart.view.*
import timber.log.Timber

class CartAdapter(
    private val remove: (cart: Cart) -> Unit,
    private val add: (cart: Cart) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cartList = emptyList<Cart>()

    fun setCartList(cartList: List<Cart>) {
        this.cartList = cartList
        Timber.d("CART: $cartList")
        notifyDataSetChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount() = cartList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cart = cartList[position]
        holder.itemView.apply {
            tvProductName?.text = cart.product?.name
            tvProductQuantity?.text = cart.quantity.toString()

            buttonRemove.setOnClickListener { remove(cart) }

            buttonAdd.setOnClickListener { add(cart) }
        }
    }

}
