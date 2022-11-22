package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName

data class UserDetails(

    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("address"       ) var address      : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("image"         ) var image        : String? = null

)
