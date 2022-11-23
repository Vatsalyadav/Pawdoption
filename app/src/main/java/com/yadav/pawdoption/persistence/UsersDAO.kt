package com.yadav.pawdoption.persistence

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.yadav.pawdoption.model.*

class UsersDAO : IUsersDAO {
    val TAG = "PetsDAO"
    private val petsListByShelters: MutableList<ShelterPet> = mutableListOf()
    private val petsList: MutableList<ShelterPet> = mutableListOf()

    private var shelters = MutableLiveData<HashMap<String, Shelter>>()

//    override fun getShelters(): MutableLiveData<HashMap<String, Shelter>> {
//        val sheltersReference = FirebaseDatabaseSingleton.getSheltersReference()
//        sheltersReference.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                shelters.value = snapshot.getValue<HashMap<String,Shelter>>()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })
//        return shelters
//    }


    override fun getUserList(): MutableLiveData<HashMap<String, User>> {
        TODO("Not yet implemented")
    }
}