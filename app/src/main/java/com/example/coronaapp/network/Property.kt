package com.example.coronaapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Property(
    val date: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
    ) : Parcelable