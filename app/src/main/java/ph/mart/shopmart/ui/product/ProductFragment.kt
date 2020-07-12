package ph.mart.shopmart.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ph.mart.shopmart.R
import ph.mart.shopmart.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product.*

@AndroidEntryPoint
class ProductFragment : BaseFragment(R.layout.fragment_product) {

    private val viewModel by viewModels<ProductViewModel>()

    private val productViewAdapter = ProductViewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewProduct.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            adapter = productViewAdapter
        }

        eventUI()

        subscribeUI()
    }

    private fun eventUI() {
        with(viewModel) {
            swipeRefreshLayoutProduct.setOnRefreshListener { getData() }
        }
    }

    private fun subscribeUI() {
        with(viewModel) {
            loadingLiveData.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayoutProduct.isRefreshing = it
            })

            productListLiveData.observe(viewLifecycleOwner, Observer {
                productViewAdapter.setProduct(it)
            })
        }
    }
}