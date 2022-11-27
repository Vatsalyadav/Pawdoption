package com.yadav.pawdoption.dataclass

import com.yadav.pawdoption.model.PendingAdoption
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.model.User

data class PendingAdoptionData(
    val pendionAdoption: PendingAdoption,
    val shelterPet: ShelterPet?,
    val user: User?
)
