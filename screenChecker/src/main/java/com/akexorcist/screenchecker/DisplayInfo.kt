package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisplayInfo(
    val name: String?,
    val mode: DisplayMode?,
    val rotation: Int,
    val colorSpace: String?,
) : Parcelable
