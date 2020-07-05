package com.example.shopmart.data.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String?,
    val image: String,
    val price: Long
) : Parcelable {
    @IgnoredOnParcel
    val pricePhp = "Php. $price"
}
