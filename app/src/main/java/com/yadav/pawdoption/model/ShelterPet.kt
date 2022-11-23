package com.yadav.pawdoption.model

import com.google.gson.annotations.SerializedName


data class ShelterPet(

    @SerializedName("id"          ) var id: String? = null,
    @SerializedName("name"        ) var name: String? = null,
    @SerializedName("age"         ) var age: Int?    = null,
    @SerializedName("breed"       ) var breed: String? = null,
    @SerializedName("description" ) var description: String? = null,
    @SerializedName("imageURL"    ) var imageURL: ArrayList<String> = arrayListOf(),

    var shelterName: String
)