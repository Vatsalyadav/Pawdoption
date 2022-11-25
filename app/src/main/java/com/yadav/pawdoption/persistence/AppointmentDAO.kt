package com.yadav.pawdoption.persistence

import com.google.firebase.database.ktx.getValue
import com.yadav.pawdoption.model.Veterinarian

class AppointmentDAO: IAppointmentDAO {
    override fun createSchedule(shelterId: String, vet: Veterinarian) {
        val sheltersReference = FirebaseDatabaseSingleton.getSheltersReference()
        val vetsRef = sheltersReference.child(shelterId).child("veterinarians")
        vetsRef.get().addOnSuccessListener {
            val vets = it.getValue<ArrayList<Veterinarian>>()
            val vetsSize = vets?.size ?: 0
            vet.id = vetsSize.toString()
            vetsRef.child(vetsSize.toString()).setValue(vet)
        }
    }
}