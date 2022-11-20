package com.yadav.pawdoption.dao

import com.yadav.pawdoption.model.PendingAdoption


interface IPendingAdoptionDAO {
    fun getAdoptionList(shelterId: String): MutableList<PendingAdoption>
}