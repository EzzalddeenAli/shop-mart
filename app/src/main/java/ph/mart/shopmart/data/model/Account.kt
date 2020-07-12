package ph.mart.shopmart.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    val accountId: String,
    val Name: String,
    val username: String,
    val password: String
) : Parcelable