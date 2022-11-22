package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Donations (

    @SerializedName("donationId")
    var donationId : String? = null,

    @SerializedName("userId")
    var userId : String? = null,

    @SerializedName("shelterId")
    var shelterId : String? = null
)