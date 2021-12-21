package ng.adashi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.databinding.ActivityMainBinding
import ng.adashi.network.SessionManager
import ng.adashi.utils.App
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController

    @Inject lateinit var sessions : SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.token = sessions.fetchAuthToken()

        // data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // This is to find my NavHost Fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setupNavigation()

        binding.bottomNavigation.setupWithNavController(navController)

    }

    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.saversFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.transactionsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.moreFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}