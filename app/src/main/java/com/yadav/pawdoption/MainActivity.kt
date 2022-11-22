package com.yadav.pawdoption

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yadav.pawdoption.databinding.ActivityMainBinding
import com.yadav.pawdoption.model.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun usersDummyData() {

        val user : Users = Users()

        val pet1 : Pet = Pet("1002", "2002")
        val pet2 : Pet = Pet("1003", "2001")
        val petsList = ArrayList<Pet>()
        petsList.add(pet1)
        petsList.add(pet2)

        val userLovedPet1 : UserLovedPet = UserLovedPet("1001", "2001")
        val userLovedPet2 : UserLovedPet = UserLovedPet("1002", "2002")
        val userLovedPet3 : UserLovedPet = UserLovedPet("1004", "2002")
        val userLovedPetList = ArrayList<UserLovedPet>()
        userLovedPetList.add(userLovedPet1)
        userLovedPetList.add(userLovedPet2)
        userLovedPetList.add(userLovedPet3)

        val appointment1 : Appointment = Appointment("3001", "2001","12", "4001")
        val appointment2 : Appointment = Appointment("3002", "2001","13", "4001")
        val appointmentList = ArrayList<Appointment>()
        appointmentList.add(appointment1)
        appointmentList.add(appointment2)

        val donation1 : Donations = Donations("5001", "0001", "2001")
        val donation2 : Donations = Donations("5002", "0001", "2002")
        val donationsList = ArrayList<Donations>()
        donationsList.add(donation1)
        donationsList.add(donation2)

        user.userId = "0001"
        user.name = "Vatsal Yadav"
        user.address = "1030 South Park Street"
        user.image = "url"
        user.mobileNumber = "9029029025"
        user.pets = petsList
        user.lovedPets = userLovedPetList
        user.appointments = appointmentList
        user.donations = donationsList

    }

    private fun postToFirebase() {
        val database = Firebase.database
//        val myRef = database.getReference("Users").child(user.userId!!)
//        myRef.setValue(user)
    }
}