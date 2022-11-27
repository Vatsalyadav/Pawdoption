package com.yadav.pawdoption.persistence

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.yadav.pawdoption.model.*

class UsersDAO : IUsersDAO {
    val TAG = "UsersDAO"

    private var users = MutableLiveData<HashMap<String, User>>()
    private var usersType = MutableLiveData<String>()

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

    override fun setCurrentUserTypeByUid(uid: String) {
        val usersReference = FirebaseDatabaseSingleton.getUserTypeReference()
        usersReference.child(uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                usersType.value = snapshot.value as String
                usersType.value = "petAdopter"
                Log.e("UsersDAO", "UserType: "+usersType)
                FirebaseDatabaseSingleton.setCurrentUserType(usersType.value!!)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }
    override fun getCurrentUserTypeByUid(): MutableLiveData<String> {
        return usersType
    }

    override fun  getUserById(userId: String): Task<DataSnapshot> {

        return FirebaseDatabaseSingleton.getUsersReference().child(userId).get()
    }
}