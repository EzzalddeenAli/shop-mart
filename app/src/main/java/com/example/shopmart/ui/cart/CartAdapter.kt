package com.example.shopmart.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopmart.data.model.Cart
import com.example.shopmart.databinding.ItemCartBinding

class CartAdapter(
    private val viewModel: CartViewModel
) : ListAdapter<Cart, CartAdapter.ViewHolder>(CartDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: CartViewModel, item: Cart, position: Int) {

            binding.viewmodel = viewModel
            binding.position = position
            binding.cart = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCartBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun submitList(list: MutableList<Cart>?) {
        super.submitList(list)
        notifyDataSetChanged()
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