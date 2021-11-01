package ng.adashi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
import ng.adashi.databinding.ActivityMainBinding
import ng.adashi.utils.App


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController
    lateinit var drawer : DrawerLayout
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // This is to find my NavHost Fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // initialized drawer binding
        drawer =  binding.drawerLayout

        //for appbar & drawer layout
        setSupportActionBar(binding.toolbar)
        val appBarConfig = AppBarConfiguration(setOf(R.id.homeFragment), binding.drawerLayout)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        NavigationUI.setupWithNavController(
            binding.toolbar,
            navController,
            appBarConfig
        )
        setupWithNavController(binding.navview,navController)
        NavigationUI.setupActionBarWithNavController(this,navController,drawer)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupNavigation()

    }


    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    supportActionBar?.show()
                    //binding.toolbar.title = "Good Morning 👋🏽"
                    binding.toolbar.setTitleTextAppearance(this,R.style.TextAppearance_AppCompat_Title)
                    binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
                R.id.splashScreenFragment -> {
                    supportActionBar?.hide()
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.welcomeFragment -> {
                    supportActionBar?.hide()
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.loginFragment -> {
                    supportActionBar?.show()
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.signupFragment -> {
                    supportActionBar?.show()
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    supportActionBar?.show()
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_container)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                binding.drawerLayout.openDrawer(GravityCompat.START)

        }

        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment_container)) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}