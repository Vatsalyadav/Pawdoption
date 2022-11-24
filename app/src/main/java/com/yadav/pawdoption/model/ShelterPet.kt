package com.yadav.pawdoption.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShelterPet(

    @SerializedName("id"          ) var id: String? = null,
    @SerializedName("name"        ) var name: String? = null,
    @SerializedName("age"         ) var age: Int?    = null,
    @SerializedName("breed"       ) var breed: String? = null,
    @SerializedName("description" ) var description: String? = null,


    var shelterName: String = "Default Shelter Name YOLO"
): Parcelable {
    @IgnoredOnParcel
    @SerializedName("imageURL"    ) var imageURL: ArrayList<String> = arrayListOf()
}