package com.akexorcist.screenchecker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisplayInfo(
    val name: String?,
    val modeId: Int,
    val colorSpace: String?
) : Parcelable