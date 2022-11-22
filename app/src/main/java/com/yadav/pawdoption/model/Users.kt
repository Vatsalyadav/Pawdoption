package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Users (

    @SerializedName("userId")
    var userId  : String? = null,

    @SerializedName("userDetails")
    var userDetails: UserDetails? = null,

    @SerializedName("pets")
    var pets : ArrayList<Pet>? = null,

    @SerializedName("appointment")
    var appointments : ArrayList<Appointment>? = null,

    @SerializedName("lovedPets")
    var lovedPets : ArrayList<UserLovedPet>? = null,

    @SerializedName("donations")
    var donations : ArrayList<Donations>? = null

)