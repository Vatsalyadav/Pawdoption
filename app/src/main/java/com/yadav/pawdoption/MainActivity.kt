package com.yadav.pawdoption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val user : User = User()

        val pet1 : UserPet = UserPet("1002", "2002")
        val pet2 : UserPet = UserPet("1003", "2001")
        val petsList = ArrayList<UserPet>()
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

//        val donation1 : ShelterDonation = ShelterDonation("5001", "0001", "2001")
//        val donation2 : ShelterDonation = ShelterDonation("5002", "0001", "2002")
//        val donationsList = ArrayList<ShelterDonation>()
//        donationsList.add(donation1)
//        donationsList.add(donation2)

        val donation1: UserDonation = UserDonation("1", "2001", "Donation made ", 100.00, "1/22/22 12:00:06")
        val donation2: UserDonation = UserDonation("2", "2001", "Donation made to shelter ", 10.00, "1/22/22 06:04:04")

        val donationsList = ArrayList<UserDonation>()
        donationsList.add(donation1)
        donationsList.add(donation2)

        user.id = "0001"
        user.name = "Vatsal Yadav"
        user.address = "1030 South Park Street"
        user.image = "url"
        user.mobileNumber = "9029029025"
        user.pets = petsList
        user.lovedPets = userLovedPetList
        user.appointments = appointmentList
        user.donations = donationsList

    }

    private fun shelterDummyData(){

        val pets = ArrayList<ShelterPet>();


        val images: ArrayList<String> = ArrayList()
        images.add("https://firebasestorage.googleapis.com/v0/b/cosmic-kite-278709.appspot.com/o/Screen%20Shot%202022-10-14%20at%205.59.47%20PM.png?alt=media&token=7c161da6-dbe0-4885-8500-b77d24ea1114")
        images.add("https://firebasestorage.googleapis.com/v0/b/cosmic-kite-278709.appspot.com/o/Screen%20Shot%202022-10-15%20at%206.07.25%20PM.png?alt=media&token=b6f38fa9-3144-4ebe-97f0-021c58a892b0")


        val pet1: ShelterPet = ShelterPet("1", "Pet1", 4, "Retrievers (Labrador)", "very nice pet", images)
        val pet2: ShelterPet = ShelterPet("2", "Pet2", 5, "French Bulldogs", "very nice pet", images)

        pets.add(pet1);
        pets.add(pet2);

        val donations: ArrayList<ShelterDonation> = ArrayList<ShelterDonation>();

        val donation1: ShelterDonation = ShelterDonation("1", "001", "Donation made ", 100.00, "1/22/22 12:00:06")
        val donation2: ShelterDonation = ShelterDonation("2", "001", "Donation made to shelter ", 10.00, "1/22/22 06:04:04")

        donations.add(donation1);
        donations.add(donation2);


        val shelter: Shelter = Shelter("2001",
            "Dummy Shelter",
            "Shelter for the dogs",
            "Bayers road, Halifax, NS",
            44.65423,
            -63.625859,
            pets,
            donations,
            arrayListOf()
        );



    }

    private fun postToFirebase() {
        val database = Firebase.database
//        val myRef = database.getReference("Users").child(user.userId!!)
//        myRef.setValue(user)
    }
}