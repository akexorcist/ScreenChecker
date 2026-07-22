package com.akexorcist.screenchecker

import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.Surface
import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenInfoTextParserTest {
    private val lineSeparator = System.lineSeparator()

    // region size()
    @Test
    fun `size maps known screen layout size constants`() {
        assertEquals("Small", ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_SMALL))
        assertEquals("Normal", ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_NORMAL))
        assertEquals("Large", ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_LARGE))
        assertEquals("Extra Large", ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_XLARGE))
        assertEquals("Undefined", ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_UNDEFINED))
    }

    @Test
    fun `size falls back to Unknown for unrecognized value`() {
        assertEquals("Unknown", ScreenInfoTextParser.size(9999))
    }
    // endregion

    // region density()
    @Test
    fun `density maps known density buckets`() {
        assertEquals("Low", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_LOW))
        assertEquals("Medium", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_MEDIUM))
        assertEquals("High", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_HIGH))
        assertEquals("Extra High", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_XHIGH))
        assertEquals("Extra Extra High", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_XXHIGH))
        assertEquals("Extra Extra Extra High", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_XXXHIGH))
        assertEquals("TV", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_TV))
        assertEquals("Density 420", ScreenInfoTextParser.density(DisplayMetrics.DENSITY_420))
    }

    @Test
    fun `density falls back to Unknown Density with the raw value for unrecognized dpi`() {
        assertEquals("Unknown Density (123)", ScreenInfoTextParser.density(123))
    }
    // endregion

    // region rotation()
    @Test
    fun `rotation maps known surface rotation constants to degrees`() {
        assertEquals("0°", ScreenInfoTextParser.rotation(Surface.ROTATION_0))
        assertEquals("90°", ScreenInfoTextParser.rotation(Surface.ROTATION_90))
        assertEquals("180°", ScreenInfoTextParser.rotation(Surface.ROTATION_180))
        assertEquals("270°", ScreenInfoTextParser.rotation(Surface.ROTATION_270))
    }

    @Test
    fun `rotation falls back to Unknown Rotation for unrecognized value`() {
        assertEquals("Unknown Rotation", ScreenInfoTextParser.rotation(42))
    }
    // endregion

    // region layout()
    @Test
    fun `layout maps known screen layout long constants`() {
        assertEquals("Long", ScreenInfoTextParser.layout(Configuration.SCREENLAYOUT_LONG_YES))
        assertEquals("Not Long", ScreenInfoTextParser.layout(Configuration.SCREENLAYOUT_LONG_NO))
        assertEquals("Undefined", ScreenInfoTextParser.layout(Configuration.SCREENLAYOUT_LONG_UNDEFINED))
    }

    @Test
    fun `layout falls back to Unknown for unrecognized value`() {
        assertEquals("Unknown", ScreenInfoTextParser.layout(9999))
    }
    // endregion

    // region colorMode()
    @Test
    fun `colorMode reports Not supported when no flags are set`() {
        val colorMode = ColorMode(hdr = false, wideColorGamut = false, hdrSdrRatio = false)
        assertEquals("Not supported", ScreenInfoTextParser.colorMode(colorMode))
    }

    @Test
    fun `colorMode reports a single flag`() {
        assertEquals(
            "HDR",
            ScreenInfoTextParser.colorMode(ColorMode(hdr = true, wideColorGamut = false, hdrSdrRatio = false)),
        )
        assertEquals(
            "Wide Color Gamut",
            ScreenInfoTextParser.colorMode(ColorMode(hdr = false, wideColorGamut = true, hdrSdrRatio = false)),
        )
        assertEquals(
            "HDR/SDR",
            ScreenInfoTextParser.colorMode(ColorMode(hdr = false, wideColorGamut = false, hdrSdrRatio = true)),
        )
    }

    @Test
    fun `colorMode joins all flags in declaration order separated by newlines`() {
        val colorMode = ColorMode(hdr = true, wideColorGamut = true, hdrSdrRatio = true)
        val expected = listOf("HDR", "Wide Color Gamut", "HDR/SDR").joinToString(lineSeparator)
        assertEquals(expected, ScreenInfoTextParser.colorMode(colorMode))
    }
    // endregion

    // region multitouch()
    @Test
    fun `multitouch maps known multitouch levels`() {
        assertEquals("5+ Points", ScreenInfoTextParser.multitouch(Multitouch.JAZZHAND))
        assertEquals("2-5 Points", ScreenInfoTextParser.multitouch(Multitouch.DISTINCT))
        assertEquals("2 Points", ScreenInfoTextParser.multitouch(Multitouch.SIMPLE))
    }

    @Test
    fun `multitouch reports Not supported for unsupported and unrecognized values`() {
        assertEquals("Not supported", ScreenInfoTextParser.multitouch(Multitouch.UNSUPPORTED))
        assertEquals("Not supported", ScreenInfoTextParser.multitouch(999))
    }
    // endregion

    // region uiMode()
    @Test
    fun `uiMode maps known ui mode types without night flag`() {
        assertEquals(
            "Normal",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_NORMAL, Configuration.UI_MODE_NIGHT_NO)),
        )
        assertEquals(
            "Car",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_CAR, Configuration.UI_MODE_NIGHT_NO)),
        )
        assertEquals(
            "Desk",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_DESK, Configuration.UI_MODE_NIGHT_NO)),
        )
        assertEquals(
            "Television",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_TELEVISION, Configuration.UI_MODE_NIGHT_NO)),
        )
        assertEquals(
            "Watch",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_WATCH, Configuration.UI_MODE_NIGHT_NO)),
        )
        assertEquals(
            "Appliance",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_APPLIANCE, Configuration.UI_MODE_NIGHT_NO)),
        )
        assertEquals(
            "VR Headset",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_VR_HEADSET, Configuration.UI_MODE_NIGHT_NO)),
        )
    }

    @Test
    fun `uiMode falls back to Unknown for an unrecognized type`() {
        assertEquals("Unknown", ScreenInfoTextParser.uiMode(UiMode(9999, Configuration.UI_MODE_NIGHT_NO)))
    }

    @Test
    fun `uiMode appends with Night suffix when night mode is active`() {
        assertEquals(
            "Normal with Night",
            ScreenInfoTextParser.uiMode(UiMode(Configuration.UI_MODE_TYPE_NORMAL, Configuration.UI_MODE_NIGHT_YES)),
        )
    }
    // endregion

    // region resolutionDp() / resolutionPx()
    @Test
    fun `resolutionDp formats width and height with dp suffix`() {
        assertEquals("1080 x 1920 dp", ScreenInfoTextParser.resolutionDp(Resolution(1080, 1920)))
    }

    @Test
    fun `resolutionPx formats width and height with px suffix`() {
        assertEquals("1080 x 1920 px", ScreenInfoTextParser.resolutionPx(Resolution(1080, 1920)))
    }
    // endregion

    // region dpi()
    @Test
    fun `dpi formats the value with a DPI suffix`() {
        assertEquals("420 DPI", ScreenInfoTextParser.dpi(420))
    }
    // endregion

    // region hdrType()
    @Test
    fun `hdrType reports Not supported for null input`() {
        assertEquals("Not supported", ScreenInfoTextParser.hdrType(null))
    }

    @Test
    fun `hdrType reports Not supported when no flags are set`() {
        val hdrType = HdrType(dolbyVision = false, hdr10 = false, hdr10Plus = false, hlg = false)
        assertEquals("Not supported", ScreenInfoTextParser.hdrType(hdrType))
    }

    @Test
    fun `hdrType joins all supported flags in declaration order separated by newlines`() {
        val hdrType = HdrType(dolbyVision = true, hdr10 = true, hdr10Plus = true, hlg = true)
        val expected = listOf("Dolby Vision", "HDR10", "HDR10+", "HLG").joinToString(lineSeparator)
        assertEquals(expected, ScreenInfoTextParser.hdrType(hdrType))
    }

    @Test
    fun `hdrType reports a single flag`() {
        assertEquals(
            "HDR10+",
            ScreenInfoTextParser.hdrType(HdrType(dolbyVision = false, hdr10 = false, hdr10Plus = true, hlg = false)),
        )
    }
    // endregion

    // region currentDisplay()
    @Test
    fun `currentDisplay falls back to Unknown Name when name is null and omits absent fields`() {
        val displayInfo = DisplayInfo(name = null, mode = null, rotation = Surface.ROTATION_0, colorSpace = null)
        assertEquals("Unknown Name", ScreenInfoTextParser.currentDisplay(displayInfo))
    }

    @Test
    fun `currentDisplay omits blank color space`() {
        val displayInfo = DisplayInfo(name = "Built-in Screen", mode = null, rotation = Surface.ROTATION_0, colorSpace = "")
        assertEquals("Built-in Screen", ScreenInfoTextParser.currentDisplay(displayInfo))
    }

    @Test
    fun `currentDisplay includes name, color space and mode when all present`() {
        val mode = DisplayMode(id = 1, refreshRate = 60, width = 1080, height = 1920)
        val displayInfo = DisplayInfo(
            name = "Built-in Screen",
            mode = mode,
            rotation = Surface.ROTATION_0,
            colorSpace = "DCI-P3",
        )
        val expected = listOf(
            "Built-in Screen",
            "DCI-P3",
            "(1) 1080 x 1920${lineSeparator}60Hz",
        ).joinToString(lineSeparator)
        assertEquals(expected, ScreenInfoTextParser.currentDisplay(displayInfo))
    }
    // endregion

    // region supportedDisplayMode()
    @Test
    fun `supportedDisplayMode reports Unknown for an empty list`() {
        assertEquals("Unknown", ScreenInfoTextParser.supportedDisplayMode(emptyList()))
    }

    @Test
    fun `supportedDisplayMode formats a single mode`() {
        val modes = listOf(DisplayMode(id = 0, refreshRate = 60, width = 1080, height = 1920))
        assertEquals("(0) 1080 x 1920${lineSeparator}60Hz", ScreenInfoTextParser.supportedDisplayMode(modes))
    }

    @Test
    fun `supportedDisplayMode joins multiple modes with a blank line between them`() {
        val modes = listOf(
            DisplayMode(id = 0, refreshRate = 60, width = 1080, height = 1920),
            DisplayMode(id = 1, refreshRate = 120, width = 1080, height = 1920),
        )
        val expected = "(0) 1080 x 1920${lineSeparator}60Hz$lineSeparator$lineSeparator(1) 1080 x 1920${lineSeparator}120Hz"
        assertEquals(expected, ScreenInfoTextParser.supportedDisplayMode(modes))
    }
    // endregion
}
