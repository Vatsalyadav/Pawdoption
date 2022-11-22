package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Appointment (

    @SerializedName("id")
    var id : String? = null,

    @SerializedName("shelter_id")
    var shelterId : String? = null,

    @SerializedName("time")
    var time : String? = null,

    @SerializedName("vet_id")
    var vetId : String? = null

)