package com.yadav.pawdoption.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

//https://stackoverflow.com/questions/61758963/how-to-ignore-fields-when-using-parcelize-annotation-in-kotlin
@Parcelize
data class ShelterPet(

    @SerializedName("id"          ) var id: String? = null,
    @SerializedName("name"        ) var name: String? = null,
    @SerializedName("age"         ) var age: Int?    = null,
    @SerializedName("breed"       ) var breed: String? = null,
    @SerializedName("description" ) var description: String? = null,

    var shelterName: String = "Shelter Name",
    var shelterId: String = ""
): Parcelable {
    @IgnoredOnParcel
    @SerializedName("imageURL"    ) var imageURL: ArrayList<String> = arrayListOf()
}