package ph.mart.shopmart.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ph.mart.shopmart.R
import ph.mart.shopmart.data.model.Product
import ph.mart.shopmart.util.loadImage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_product.view.*

class ProductViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val storage = FirebaseStorage.getInstance()

    private var productList = emptyList<Product>()

    fun setProduct(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = productList[position]
        holder.itemView.apply {


            ivProductImage.loadImage(storage.getReferenceFromUrl(product.image))
            tvProductName.text = product.name
            tvProductPrice.text = product.price.toString()

            setOnClickListener {
                findNavController().navigate(
                    ProductFragmentDirections.productToProductDetail(product)
                )
            }
        }
    }
}