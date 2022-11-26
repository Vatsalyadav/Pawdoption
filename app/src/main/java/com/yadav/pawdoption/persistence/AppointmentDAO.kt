package com.yadav.pawdoption.persistence

import com.google.firebase.database.ktx.getValue
import com.yadav.pawdoption.model.BookedSlot
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

    override fun bookAppointment(shelterId: String, vetId: String, bookedSlot: BookedSlot) {
        val sheltersReference = FirebaseDatabaseSingleton.getSheltersReference()
        val bookedSlotRef = sheltersReference.child(shelterId).child("veterinarians").child(vetId).child("bookedSlots")
        bookedSlotRef.get().addOnSuccessListener {
            val bookedSlots = it.getValue<ArrayList<BookedSlot>>()
            val bookedSlotsSize = bookedSlots?.size ?: 0
            bookedSlot.id = bookedSlotsSize.toString()
            bookedSlotRef.child(bookedSlotsSize.toString()).setValue(bookedSlot)
        }
    }
}