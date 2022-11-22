package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Users(

    @SerializedName("userId")
    var userId: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("address")
    var address: String? = null,

    @SerializedName("mobile_number")
    var mobileNumber: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("pets")
    var pets: ArrayList<Pet>? = null,

    @SerializedName("appointment")
    var appointments: ArrayList<Appointment>? = null,

    @SerializedName("lovedPets")
    var lovedPets: ArrayList<UserLovedPet>? = null,

    @SerializedName("donations")
    var donations: ArrayList<Donations>? = null

    )