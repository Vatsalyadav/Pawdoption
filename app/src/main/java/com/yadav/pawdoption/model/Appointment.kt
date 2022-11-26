package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Appointment (

    @SerializedName("shelterName")
    val shelterName: String,
    @SerializedName("vetName")
    val vetName: String,
    @SerializedName("vetQualification")
    val vetQualification: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("time")
    val time: String

)