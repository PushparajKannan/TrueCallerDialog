package com.example.turecallerdialog.ui.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.turecallerdialog.R
import com.example.turecallerdialog.databinding.ActivityHomeBinding
import com.google.android.material.appbar.MaterialToolbar

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    lateinit var binding: ActivityHomeBinding
    override fun onActivityCreated(dataBinder: ActivityHomeBinding, savedInstanceState: Bundle?) {
        binding = dataBinder
        val navController = findNavController(R.id.nav_host_fragment)


        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_main,
                        R.id.nav_contact
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.collapsingToolbarLayoutHomeScreen.setupWithNavController(toolbar, navController, appBarConfiguration)




        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when(destination.id){

                R.id.nav_main ->{
                    binding.fab.visibility = View.VISIBLE
                    
                }
                R.id.nav_contact ->{
                    binding.fab.visibility = View.GONE

                    supportActionBar?.let {
                        it.setDisplayHomeAsUpEnabled(true)
                    }


                }

            }
        }

        binding.fab.setOnClickListener {
            v->
            val bundle = Bundle()
            bundle.putString("type","MoreContact")
            navController.navigate(R.id.nav_contact,bundle)
        }

        // navView.
        //navView.setupWithNavController(navController)
        /*navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { bool ->
            drawerLayout.closeDrawer(GravityCompat.END)
            return@OnNavigationItemSelectedListener  true

        })*/


    }

    override fun onSupportNavigateUp(): Boolean {
        // This makes the action up button work when <- is displayed
        return Navigation.findNavController(
                this,
                R.id.nav_host_fragment
        ).navigateUp()
                || super.onSupportNavigateUp()
    }
}