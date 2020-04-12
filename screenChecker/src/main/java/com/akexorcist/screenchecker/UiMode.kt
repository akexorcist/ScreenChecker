package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiMode(
    val type: Int,
    val night: Int
) : Parcelable
