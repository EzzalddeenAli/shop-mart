package ph.mart.shopmart.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ph.mart.shopmart.data.model.Cart
import ph.mart.shopmart.databinding.ItemCartBinding
import ph.mart.shopmart.util.loadImage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_product.view.*

class CartAdapter(
    private val viewModel: CartViewModel
) : ListAdapter<Cart, CartAdapter.ViewHolder>(CartDiffCallback()) {

    private val storage = FirebaseStorage.getInstance()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            item?.product?.let {
                Navigation.findNavController(holder.itemView).navigate(
                    CartFragmentDirections.cartToProductDetail(it)
                )
            }
        }

        holder.bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cart, position: Int) {

            item.product?.image?.let {
                binding.root.ivProductImage.loadImage(storage.getReferenceFromUrl(it))
            }

            binding.viewmodel = viewModel
            binding.position = position
            binding.cart = item
            binding.executePendingBindings()
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