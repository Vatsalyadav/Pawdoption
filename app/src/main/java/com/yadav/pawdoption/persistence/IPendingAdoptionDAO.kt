package com.yadav.pawdoption.persistence

import com.yadav.pawdoption.model.PendingAdoption


interface IPendingAdoptionDAO {
    fun getAdoptionList(shelterId: String): MutableList<PendingAdoption>
}