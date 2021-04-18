package com.akexorcist.screenchecker

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.ColorSpace
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import androidx.annotation.RequiresApi

object ScreenUtility {
    private const val DEFAULT_DPI = DisplayMetrics.DENSITY_DEFAULT.toFloat()

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getDeviceResolutionPx(activity: Activity): Resolution {
        return getDeviceScreenResolutionPx(activity)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getDeviceResolutionDp(activity: Activity): Resolution {
        val display = getDisplay(activity)
        val densityDpi = getDensityDpi(activity, display)
        val (x, y) = getDeviceScreenResolutionPx(activity, display)
        val xDp = (x * (DEFAULT_DPI / densityDpi)).toInt()
        val yDp = (y * (DEFAULT_DPI / densityDpi)).toInt()
        return Resolution(xDp, yDp)
    }

    fun getCurrentResolutionPx(activity: Activity): Resolution {
        return getCurrentScreenResolutionPx(activity)
    }

    fun getCurrentResolutionDp(activity: Activity): Resolution {
        val display = getDisplay(activity)
        val densityDpi = getDensityDpi(activity, display)
        val (x, y) = getCurrentScreenResolutionPx(activity, display)
        val xDp = (x * (DEFAULT_DPI / densityDpi)).toInt()
        val yDp = (y * (DEFAULT_DPI / densityDpi)).toInt()
        return Resolution(xDp, yDp)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun getAppResolutionPx(rootView: View): Resolution {
        return getAppScreenResolutionPx(rootView)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun getAppResolutionDp(activity: Activity, rootView: View): Resolution {
        val display = getDisplay(activity)
        val densityDpi = getDensityDpi(activity, display)
        val (x, y) = getAppScreenResolutionPx(rootView)
        val xDp = (x * (DEFAULT_DPI / densityDpi)).toInt()
        val yDp = (y * (DEFAULT_DPI / densityDpi)).toInt()
        return Resolution(xDp, yDp)
    }

    private fun getCurrentScreenResolutionPx(activity: Activity, display: Display = getDisplay(activity)): Resolution {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                val getRawHeightMethod = Display::class.java.getMethod("getRawHeight")
                val getRawWidthMethod = Display::class.java.getMethod("getRawWidth")
                Resolution(getRawWidthMethod.invoke(display) as Int, getRawHeightMethod.invoke(display) as Int)
            } catch (e: Exception) {
                @Suppress("DEPRECATION")
                Resolution(display.width, display.height)
            }
        } else {
            val outMetrics = DisplayMetrics()
            display.getRealMetrics(outMetrics)
            Resolution(outMetrics.widthPixels, outMetrics.heightPixels)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun getDeviceScreenResolutionPx(activity: Activity, display: Display = getDisplay(activity)): Resolution {
        val resolutionX = display.mode.physicalWidth
        val resolutionY = display.mode.physicalHeight
        return Resolution(resolutionX, resolutionY)
    }

    private fun getAppScreenResolutionPx(rootView: View): Resolution {
        val resolutionX = rootView.measuredWidth
        val resolutionY = rootView.measuredHeight
        return Resolution(resolutionX, resolutionY)
    }

    fun getDensity(activity: Activity): Int {
        val display = getDisplay(activity)
        return getDensityDpi(activity, display)
    }

    fun getSize(context: Context): Int {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    }

    fun getLayout(context: Context): Int {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_LONG_MASK
    }

    fun getColorMode(context: Context): ColorMode =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val hdr = context.resources.configuration.colorMode and Configuration.COLOR_MODE_HDR_MASK
            val wideColorGamut = context.resources.configuration.colorMode and Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_MASK
            ColorMode(
                hdr = hdr == Configuration.COLOR_MODE_HDR_YES,
                wideColorGamut = wideColorGamut == Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_YES
            )
        } else {
            ColorMode(
                hdr = false,
                wideColorGamut = false
            )
        }

    fun getHdrType(activity: Activity, display: Display = getDisplay(activity)): HdrType? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val supportedHdrTypes: IntArray = display.hdrCapabilities.supportedHdrTypes
            HdrType(
                dolbyVision = supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_DOLBY_VISION),
                hdr10 = supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HDR10),
                hdr10Plus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HDR10_PLUS)
                } else false,
                hlg = supportedHdrTypes.contains(Display.HdrCapabilities.HDR_TYPE_HLG)
            )
        } else null
    }

    fun getUiMode(context: Context): UiMode {
        val type = context.resources.configuration.uiMode and Configuration.UI_MODE_TYPE_MASK
        val night = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return UiMode(type, night)
    }

    fun getMultitouch(context: Context): Int {
        return when {
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND) ->
                Multitouch.JAZZHAND
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT) ->
                Multitouch.DISTINCT
            context.packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH) ->
                Multitouch.SIMPLE
            else ->
                Multitouch.UNSUPPORTED
        }
    }

    private fun getDisplay(activity: Activity): Display {
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.display ?: activity.windowManager.defaultDisplay
        } else {
            activity.windowManager.defaultDisplay
        }
    }


    private fun getDensityDpi(activity: Activity, display: Display): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.resources.configuration.densityDpi
        } else {
            val dm = DisplayMetrics()
            @Suppress("DEPRECATION")
            display.getMetrics(dm)
            dm.densityDpi
        }
    }
}
