package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Appointment (

    @SerializedName("appointmentId")
    var appointmentId : String? = null,

    @SerializedName("shelterId")
    var shelterId : String? = null,

    @SerializedName("time")
    var time : String? = null,

    @SerializedName("vetId")
    var vetId : String? = null

)