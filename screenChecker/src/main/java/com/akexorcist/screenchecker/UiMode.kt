package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiMode(
    val type: Int,
    val night: Int
) : Parcelable
