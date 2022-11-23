package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class UserType(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("userType")
    var userType: String? = null,


)