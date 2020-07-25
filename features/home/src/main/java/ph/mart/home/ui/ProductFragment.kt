package ph.mart.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product.*
import ph.mart.base.BaseFragment
import ph.mart.home.R

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