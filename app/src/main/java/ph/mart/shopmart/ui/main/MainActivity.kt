package ph.mart.shopmart.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ph.mart.shopmart.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbarMain)

        setupNavigation()

        subscribeUI()
    }

    private fun setupNavigation() {
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fragmentHome, R.id.fragmentCart, R.id.fragmentAccountManager)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentHome, R.id.fragmentCart, R.id.fragmentAccountManager -> {
                    supportActionBar?.hide()
                    bottomNavigationView.isVisible = true
                }
                R.id.productDetailFragment -> {
                    supportActionBar?.show()
                    bottomNavigationView.isVisible = false
                }
                else -> {
                    supportActionBar?.show()
                    bottomNavigationView.isVisible = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp()
    }

    private fun subscribeUI() {
        with(viewModel) {
            isLoggedInLiveData.observe(this@MainActivity, Observer {
                bottomNavigationView.menu.getItem(1).isVisible = it
            })
        }
    }
}