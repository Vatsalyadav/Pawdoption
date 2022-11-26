package com.yadav.pawdoption.persistence

import androidx.lifecycle.MutableLiveData
import com.yadav.pawdoption.model.User

interface IUsersDAO {

    fun getUserList(): MutableLiveData<HashMap<String, User>>
    fun setCurrentUserTypeByUid(uid: String)
    fun getCurrentUserTypeByUid(): MutableLiveData<String>
}