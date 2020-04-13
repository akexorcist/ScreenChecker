package com.akexorcist.screenchecker

import android.content.res.Configuration
import android.util.DisplayMetrics

object ScreenInfoTextParser {
    fun size(screenSize: Int): String = when (screenSize) {
        Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small"
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal"
        Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large"
        Configuration.SCREENLAYOUT_SIZE_XLARGE -> "Extra Large"
        Configuration.SCREENLAYOUT_SIZE_UNDEFINED -> "Undefined"
        else -> "Unknown"
    }

    fun density(density: Int): String = when (density) {
        DisplayMetrics.DENSITY_LOW -> "Low"
        DisplayMetrics.DENSITY_MEDIUM -> "Medium"
        DisplayMetrics.DENSITY_TV -> "TV"
        DisplayMetrics.DENSITY_HIGH -> "High"
        DisplayMetrics.DENSITY_XHIGH -> "Extra High"
        DisplayMetrics.DENSITY_XXHIGH -> "Extra Extra High"
        DisplayMetrics.DENSITY_XXXHIGH -> "Extra Extra Extra High"
        DisplayMetrics.DENSITY_140 -> "Density 140"
        DisplayMetrics.DENSITY_180 -> "Density 180"
        DisplayMetrics.DENSITY_200 -> "Density 200"
        DisplayMetrics.DENSITY_220 -> "Density 220"
        DisplayMetrics.DENSITY_260 -> "Density 260"
        DisplayMetrics.DENSITY_280 -> "Density 280"
        DisplayMetrics.DENSITY_300 -> "Density 300"
        DisplayMetrics.DENSITY_340 -> "Density 340"
        DisplayMetrics.DENSITY_360 -> "Density 360"
        DisplayMetrics.DENSITY_400 -> "Density 400"
        DisplayMetrics.DENSITY_420 -> "Density 420"
        DisplayMetrics.DENSITY_440 -> "Density 440"
        DisplayMetrics.DENSITY_450 -> "Density 450"
        DisplayMetrics.DENSITY_560 -> "Density 560"
        DisplayMetrics.DENSITY_600 -> "Density 600"
        else -> "Unknown Density ($density)"
    }

    fun layout(layout: Int): String = when (layout) {
        Configuration.SCREENLAYOUT_LONG_YES -> "Long"
        Configuration.SCREENLAYOUT_LONG_NO -> "Not Long"
        Configuration.SCREENLAYOUT_LONG_UNDEFINED -> "Undefined"
        else -> "Unknown"
    }

    fun colorMode(colorMode: ColorMode): String =
        if (colorMode.hdr == Configuration.COLOR_MODE_HDR_YES &&
            colorMode.wideColorGamut == Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_YES
        ) {
            "HDR & Wide Color Gamut"
        } else if (colorMode.hdr == Configuration.COLOR_MODE_HDR_YES) {
            "HDR"
        } else if (colorMode.wideColorGamut == Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_YES) {
            "Wide Color Gamut"
        } else {
            "Not supported"
        }

    fun multitouch(multitouch: Int): String = when (multitouch) {
        Multitouch.JAZZHAND -> "5+ Points"
        Multitouch.DISTINCT -> "2-5 Points"
        Multitouch.SIMPLE -> "2 Points"
        else -> "Not supported"
    }

    fun uiMode(mode: UiMode): String {
        var text: String = when (mode.type) {
            Configuration.UI_MODE_TYPE_APPLIANCE -> "Appliance"
            Configuration.UI_MODE_TYPE_CAR -> "Car"
            Configuration.UI_MODE_TYPE_DESK -> "Desk"
            Configuration.UI_MODE_TYPE_NORMAL -> "Normal"
            Configuration.UI_MODE_TYPE_TELEVISION -> "Television"
            Configuration.UI_MODE_TYPE_VR_HEADSET -> "VR Headset"
            Configuration.UI_MODE_TYPE_WATCH -> "Watch"
            else -> "Unknown"
        }
        if (mode.night == Configuration.UI_MODE_NIGHT_YES) {
            text += " with Night"
        }
        return text
    }

    fun resolutionDp(resolution: Resolution): String = "${resolution.x} x ${resolution.y} dp"

    fun resolutionPx(resolution: Resolution): String = "${resolution.x} x ${resolution.y} px"

    fun dpi(dpi: Int): String = "$dpi DPI"
}
