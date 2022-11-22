package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class BookedSlot (

    @SerializedName("id"       ) var id       : String? = null,
    @SerializedName("day"      ) var day      : String? = null,
    @SerializedName("timeSlot" ) var timeSlot : String? = null,
    @SerializedName("userId"   ) var userId   : String? = null

)