package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class Shelter (

    @SerializedName("id"          ) var id          : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("address"     ) var address     : String? = null,
    @SerializedName("latitude"    ) var latitude    : Double? = null,
    @SerializedName("longitude"   ) var longitude   : Double? = null,
    @SerializedName("pets"        ) var pets        : ArrayList<ShelterPet> = arrayListOf(),
    @SerializedName("donations"   ) var donations   : ArrayList<ShelterDonation> = arrayListOf(),
    @SerializedName("veterinarians") var veterinarians   : ArrayList<Veterinarian> = arrayListOf(),
)