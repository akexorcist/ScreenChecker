package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisplayInfo(
    val name: String?,
    val refreshRate: Float,
    val currentMode: Int,
    val supportedModeCount: Int,
    val colorSpace: String?
) : Parcelable
