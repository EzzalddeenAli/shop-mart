package ph.mart.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ph.mart.home.R
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TestFragment : Fragment(R.layout.fragment_test) {

    @Inject
    lateinit var aa: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("qwer: $aa")
    }
}