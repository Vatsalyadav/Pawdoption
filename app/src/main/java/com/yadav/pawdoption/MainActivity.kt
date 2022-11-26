package com.yadav.pawdoption

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yadav.pawdoption.persistence.UsersDAO

// https://guides.codepath.com/android/Bottom-Navigation-Views
// https://stackoverflow.com/questions/53902494/navigation-component-cannot-find-navcontroller
// https://firebase.google.com/docs/auth/android/start#check_current_auth_state
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val usersDAO = UsersDAO()
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    public override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userType = usersDAO.getUsersTypeById(currentUser.uid)
            Navigation.findNavController(this, R.id.nav_host_fragment)
                .navigate(R.id.petListFragment)
            setBottomNavigation(userType)
        }
    }

    private fun setBottomNavigation(userType: String) {
        if (userType.uppercase().equals("PETADOPTER"))
            bottomNavigationView = findViewById(R.id.bottom_nav_pet_owner)
        else
            bottomNavigationView = findViewById(R.id.bottom_nav_shelter)
        bottomNavigationView.visibility = View.VISIBLE
        bottomNavigationView.selectedItemId = R.id.pets
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pets -> {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.petListFragment)
                    true
                }
                R.id.shelters -> {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.mapsFragment)
                    true
                }
                R.id.vet -> {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.bookAppointment)
                    true
                }
//                TODO: Add others too
                else -> true
            }

        }
    }

}