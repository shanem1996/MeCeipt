package com.example.meceipt.models

import android.os.Parcel
import android.os.Parcelable

data class User (
    val id: String = "",
    val fName: String = "",
    val lName: String = "",
    val email: String = "",
    val qrCode: String = "",
    val fcmToken: String = ""
        ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(p0: Parcel, p1: Int) = with(p0) {
        writeString(id)
        writeString(fName)
        writeString(email)
        writeString(lName)
        writeString(qrCode)
        writeString(fcmToken)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

