package com.yadav.pawdoption.persistence

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.yadav.pawdoption.model.User

interface IUsersDAO {

    fun getUserList(): MutableLiveData<HashMap<String, User>>
    fun setCurrentUserTypeByUid(uid: String)
    fun getCurrentUserTypeByUid(): MutableLiveData<String>
    fun getUserById(userId: String): Task<DataSnapshot>
}