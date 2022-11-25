package com.yadav.pawdoption.persistence

import com.yadav.pawdoption.model.ShelterPet

interface IPetDAO {
    fun postPet(shelterId: String, pet: ShelterPet)
}