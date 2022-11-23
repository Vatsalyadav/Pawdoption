package com.yadav.pawdoption

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yadav.pawdoption.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottomNavigation()

    }

    // https://guides.codepath.com/android/Bottom-Navigation-Views
    //    https://stackoverflow.com/questions/53902494/navigation-component-cannot-find-navcontroller
    private fun setBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.pets
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pets -> {
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.petListFragment)
                    true
                }
                R.id.shelters -> {
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.shelterMapFragment)
                    true
                }
//                TODO: Add others too
                else -> true
            }

        }
    }


}