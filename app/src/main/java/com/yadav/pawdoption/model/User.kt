package com.yadav.pawdoption.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("address")
    var address: String? = null,

    @SerializedName("mobile_number")
    var mobileNumber: String? = null,

    @SerializedName("image")
    var image: String? = null,
) : Parcelable {
    @IgnoredOnParcel
    @SerializedName("pets")
    var pets: ArrayList<UserPet>? = null

    @IgnoredOnParcel
    @SerializedName("appointment")
    var appointments: ArrayList<Appointment>? = null

    @IgnoredOnParcel
    @SerializedName("lovedPets")
    var lovedPets: ArrayList<UserLovedPet>? = null

    @IgnoredOnParcel
    @SerializedName("donations")
    var donations: ArrayList<UserDonation>? = null
}