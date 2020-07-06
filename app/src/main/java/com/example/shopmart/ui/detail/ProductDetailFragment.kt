package com.example.shopmart.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shopmart.R
import com.example.shopmart.data.model.Product
import com.example.shopmart.ui.base.BaseFragment
import com.example.shopmart.util.loadImage
import com.example.shopmart.util.setupSnackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {

    private val viewModel by viewModels<ProductDetailViewModel>()

    private val args: ProductDetailFragmentArgs by navArgs()

    private val product: Product by lazy { args.product }

    @Inject
    lateinit var firebaseStorage: FirebaseStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()

        eventUI()

        subscribeUI()
    }

    private fun setData() {
        tvProductName.text = product.name
        tvProductPrice.text = product.price.toString()
        ivProductImage.loadImage(firebaseStorage.getReferenceFromUrl(product.image))
    }

    private fun eventUI() {
        with(viewModel) {
            buttonAddToCart.setOnClickListener { addToCart(product.id) }
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, Observer {
                buttonAddToCart.isEnabled = !it
            })

            errorLiveData.observe(viewLifecycleOwner, Observer {
                showSignInDialog()
            })

            showAddedToCartDialog.observe(viewLifecycleOwner, Observer {
                showAddedToCartDialog()
            })

            view?.setupSnackbar(viewLifecycleOwner, snackBarLiveData, Snackbar.LENGTH_SHORT)
        }
    }

    private fun showSignInDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.message_sign_in)
            .setPositiveButton(getString(R.string.sign_in)) { _, _ ->
                findNavController().navigate(
                    ProductDetailFragmentDirections.productDetailToAccountManager()
                )
            }
            .show()
    }

    private fun showAddedToCartDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.added_to_cart)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()

    }
}