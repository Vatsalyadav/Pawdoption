package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Donation (

    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("userID"    ) var userID    : String? = null,
    @SerializedName("shelterID" ) var shelterID : String? = null,
    @SerializedName("comment"   ) var comment   : String? = null,
    @SerializedName("amount"    ) var amount    : Double? = null,
    @SerializedName("timestamp" ) var timestamp : String? = null

)