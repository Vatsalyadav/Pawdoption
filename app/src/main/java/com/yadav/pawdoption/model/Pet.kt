package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Pet (

    @SerializedName("petId")
    var petId     : String? = null,

    @SerializedName("shelterId")
    var shelterId : String? = null

)