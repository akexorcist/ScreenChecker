package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorMode(
    val hdr: Int,
    val wideColorGamut: Int
) : Parcelable
