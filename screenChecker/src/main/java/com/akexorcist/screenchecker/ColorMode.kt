package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorMode(
    val hdr: Boolean,
    val wideColorGamut: Boolean
) : Parcelable
