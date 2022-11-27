package com.yadav.pawdoption.persistence

import androidx.lifecycle.MutableLiveData
import com.yadav.pawdoption.model.User
import com.yadav.pawdoption.model.UserLovedPet

interface IUsersDAO {

    fun getUserList(): MutableLiveData<HashMap<String, User>>
    fun setCurrentUserTypeByUid(uid: String)
    fun getCurrentUserTypeByUid(): MutableLiveData<String>
    fun setPetToLoved(userId: String, lovedPets: ArrayList<UserLovedPet>)
    fun getLovedPetsByUid(userId: String) : MutableLiveData<ArrayList<UserLovedPet>>
}