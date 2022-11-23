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
    val TAG = "UsersDAO"

    private var users = MutableLiveData<HashMap<String, User>>()

    override fun getUserList(): MutableLiveData<HashMap<String, User>> {
        val usersReference = FirebaseDatabaseSingleton.getUsersReference()
        usersReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users.value = snapshot.getValue<HashMap<String,User>>()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
        return users
    }
}