package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HdrType(
    val dolbyVision: Boolean,
    val hdr10: Boolean,
    val hdr10Plus: Boolean,
    val hlg: Boolean
) : Parcelable
