package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisplayMode(
    val id: Int,
    val refreshRate: Int,
    val width: Int,
    val height: Int,
) : Parcelable
