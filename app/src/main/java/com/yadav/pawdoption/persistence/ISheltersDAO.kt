package com.yadav.pawdoption.persistence

import androidx.lifecycle.MutableLiveData
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.ShelterPet

interface ISheltersDAO {
    fun getPetsList()
    fun getPetsListByShelterId(shelterId: String): MutableList<ShelterPet>
    fun getShelters(): MutableLiveData<HashMap<String, Shelter>>
}