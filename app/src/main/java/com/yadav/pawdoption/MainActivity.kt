package com.yadav.pawdoption

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private fun setBottomNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.pets
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                else -> true
            }

        }
    }


}