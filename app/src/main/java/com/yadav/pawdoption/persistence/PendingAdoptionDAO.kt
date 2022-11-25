package com.yadav.pawdoption.persistence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.yadav.pawdoption.model.PendingAdoption
import com.yadav.pawdoption.model.Shelter

class PendingAdoptionDAO : IPendingAdoptionDAO {

    val TAG = "PendingAdoptionDAO"

    val pendingAdoptions = MutableLiveData<ArrayList<PendingAdoption>>();
    override fun getAdoptionList(shelterId: String): MutableList<PendingAdoption> {
        TODO("Not yet implemented")
    }

    fun getAdoptionListTest(shelterId: String): MutableLiveData<ArrayList<PendingAdoption>> {
        val shelterReference = FirebaseDatabaseSingleton.getSheltersReference()
        shelterReference.child(shelterId).child("pendingAdoptions").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingAdoptions.value = snapshot.getValue<ArrayList<PendingAdoption>>()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        print(pendingAdoptions)
        return pendingAdoptions
    }

}