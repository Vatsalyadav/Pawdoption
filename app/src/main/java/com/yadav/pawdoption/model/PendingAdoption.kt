package com.yadav.pawdoption.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendingAdoption(
    var userId: String,
    var shelterId: String,
    var petId: String,
    var timestamp: String,
    var haveEnoughSpace: Boolean,
    var haveDogHouse: Boolean,
    var anyAllergic: Boolean
) : Parcelable
