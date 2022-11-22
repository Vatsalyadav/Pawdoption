package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class ShelterDonation (

    @SerializedName("donation_id")
    var donationId : String? = null,

    @SerializedName("user_id")
    var userId : String? = null,

    @SerializedName("shelter_id")
    var shelterId : String? = null
)