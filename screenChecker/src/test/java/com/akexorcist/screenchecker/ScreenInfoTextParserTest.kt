package com.akexorcist.screenchecker

import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.Surface
import io.kotest.matchers.shouldBe
import org.junit.Test

class ScreenInfoTextParserTest {
    private val lineSeparator = System.lineSeparator()

    // region size()
    @Test
    fun `size maps known screen layout size constants`() {
        ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_SMALL) shouldBe "Small"
        ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_NORMAL) shouldBe "Normal"
        ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_LARGE) shouldBe "Large"
        ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_XLARGE) shouldBe "Extra Large"
        ScreenInfoTextParser.size(Configuration.SCREENLAYOUT_SIZE_UNDEFINED) shouldBe "Undefined"
    }

    @Test
    fun `size falls back to Unknown for unrecognized value`() {
        ScreenInfoTextParser.size(9999) shouldBe "Unknown"
    }
    // endregion

    // region density()
    @Test
    fun `density maps known density buckets`() {
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_LOW) shouldBe "Low"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_MEDIUM) shouldBe "Medium"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_HIGH) shouldBe "High"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_XHIGH) shouldBe "Extra High"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_XXHIGH) shouldBe "Extra Extra High"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_XXXHIGH) shouldBe "Extra Extra Extra High"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_TV) shouldBe "TV"
        ScreenInfoTextParser.density(DisplayMetrics.DENSITY_420) shouldBe "Density 420"
    }

    @Test
    fun `density falls back to Unknown Density with the raw value for unrecognized dpi`() {
        ScreenInfoTextParser.density(123) shouldBe "Unknown Density (123)"
    }
    // endregion

    // region rotation()
    @Test
    fun `rotation maps known surface rotation constants to degrees`() {
        ScreenInfoTextParser.rotation(Surface.ROTATION_0) shouldBe "0°"
        ScreenInfoTextParser.rotation(Surface.ROTATION_90) shouldBe "90°"
        ScreenInfoTextParser.rotation(Surface.ROTATION_180) shouldBe "180°"
        ScreenInfoTextParser.rotation(Surface.ROTATION_270) shouldBe "270°"
    }

    @Test
    fun `rotation falls back to Unknown Rotation for unrecognized value`() {
        ScreenInfoTextParser.rotation(42) shouldBe "Unknown Rotation"
    }
    // endregion

    // region layout()
    @Test
    fun `layout maps known screen layout long constants`() {
        ScreenInfoTextParser.layout(Configuration.SCREENLAYOUT_LONG_YES) shouldBe "Long"
        ScreenInfoTextParser.layout(Configuration.SCREENLAYOUT_LONG_NO) shouldBe "Not Long"
        ScreenInfoTextParser.layout(Configuration.SCREENLAYOUT_LONG_UNDEFINED) shouldBe "Undefined"
    }

    @Test
    fun `layout falls back to Unknown for unrecognized value`() {
        ScreenInfoTextParser.layout(9999) shouldBe "Unknown"
    }
    // endregion

    // region colorMode()
    @Test
    fun `colorMode reports Not supported when no flags are set`() {
        val colorMode = ColorMode(hdr = false, wideColorGamut = false, hdrSdrRatio = false)
        ScreenInfoTextParser.colorMode(colorMode) shouldBe "Not supported"
    }

    @Test
    fun `colorMode reports a single flag`() {
        ScreenInfoTextParser.colorMode(
            ColorMode(hdr = true, wideColorGamut = false, hdrSdrRatio = false),
        ) shouldBe "HDR"
        ScreenInfoTextParser.colorMode(
            ColorMode(hdr = false, wideColorGamut = true, hdrSdrRatio = false),
        ) shouldBe "Wide Color Gamut"
        ScreenInfoTextParser.colorMode(
            ColorMode(hdr = false, wideColorGamut = false, hdrSdrRatio = true),
        ) shouldBe "HDR/SDR"
    }

    @Test
    fun `colorMode joins all flags in declaration order separated by newlines`() {
        val colorMode = ColorMode(hdr = true, wideColorGamut = true, hdrSdrRatio = true)
        val expected = listOf("HDR", "Wide Color Gamut", "HDR/SDR").joinToString(lineSeparator)
        ScreenInfoTextParser.colorMode(colorMode) shouldBe expected
    }
    // endregion

    // region multitouch()
    @Test
    fun `multitouch maps known multitouch levels`() {
        ScreenInfoTextParser.multitouch(Multitouch.JAZZHAND) shouldBe "5+ Points"
        ScreenInfoTextParser.multitouch(Multitouch.DISTINCT) shouldBe "2-5 Points"
        ScreenInfoTextParser.multitouch(Multitouch.SIMPLE) shouldBe "2 Points"
    }

    @Test
    fun `multitouch reports Not supported for unsupported and unrecognized values`() {
        ScreenInfoTextParser.multitouch(Multitouch.UNSUPPORTED) shouldBe "Not supported"
        ScreenInfoTextParser.multitouch(999) shouldBe "Not supported"
    }
    // endregion

    // region uiMode()
    @Test
    fun `uiMode maps known ui mode types without night flag`() {
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_NORMAL, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "Normal"
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_CAR, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "Car"
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_DESK, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "Desk"
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_TELEVISION, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "Television"
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_WATCH, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "Watch"
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_APPLIANCE, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "Appliance"
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_VR_HEADSET, Configuration.UI_MODE_NIGHT_NO),
        ) shouldBe "VR Headset"
    }

    @Test
    fun `uiMode falls back to Unknown for an unrecognized type`() {
        ScreenInfoTextParser.uiMode(UiMode(9999, Configuration.UI_MODE_NIGHT_NO)) shouldBe "Unknown"
    }

    @Test
    fun `uiMode appends with Night suffix when night mode is active`() {
        ScreenInfoTextParser.uiMode(
            UiMode(Configuration.UI_MODE_TYPE_NORMAL, Configuration.UI_MODE_NIGHT_YES),
        ) shouldBe "Normal with Night"
    }
    // endregion

    // region resolutionDp() / resolutionPx()
    @Test
    fun `resolutionDp formats width and height with dp suffix`() {
        ScreenInfoTextParser.resolutionDp(Resolution(1080, 1920)) shouldBe "1080 x 1920 dp"
    }

    @Test
    fun `resolutionPx formats width and height with px suffix`() {
        ScreenInfoTextParser.resolutionPx(Resolution(1080, 1920)) shouldBe "1080 x 1920 px"
    }
    // endregion

    // region dpi()
    @Test
    fun `dpi formats the value with a DPI suffix`() {
        ScreenInfoTextParser.dpi(420) shouldBe "420 DPI"
    }
    // endregion

    // region hdrType()
    @Test
    fun `hdrType reports Not supported for null input`() {
        ScreenInfoTextParser.hdrType(null) shouldBe "Not supported"
    }

    @Test
    fun `hdrType reports Not supported when no flags are set`() {
        val hdrType = HdrType(dolbyVision = false, hdr10 = false, hdr10Plus = false, hlg = false)
        ScreenInfoTextParser.hdrType(hdrType) shouldBe "Not supported"
    }

    @Test
    fun `hdrType joins all supported flags in declaration order separated by newlines`() {
        val hdrType = HdrType(dolbyVision = true, hdr10 = true, hdr10Plus = true, hlg = true)
        val expected = listOf("Dolby Vision", "HDR10", "HDR10+", "HLG").joinToString(lineSeparator)
        ScreenInfoTextParser.hdrType(hdrType) shouldBe expected
    }

    @Test
    fun `hdrType reports a single flag`() {
        ScreenInfoTextParser.hdrType(
            HdrType(dolbyVision = false, hdr10 = false, hdr10Plus = true, hlg = false),
        ) shouldBe "HDR10+"
    }
    // endregion

    // region currentDisplay()
    @Test
    fun `currentDisplay falls back to Unknown Name when name is null and omits absent fields`() {
        val displayInfo = DisplayInfo(name = null, mode = null, rotation = Surface.ROTATION_0, colorSpace = null)
        ScreenInfoTextParser.currentDisplay(displayInfo) shouldBe "Unknown Name"
    }

    @Test
    fun `currentDisplay omits blank color space`() {
        val displayInfo = DisplayInfo(name = "Built-in Screen", mode = null, rotation = Surface.ROTATION_0, colorSpace = "")
        ScreenInfoTextParser.currentDisplay(displayInfo) shouldBe "Built-in Screen"
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
        ScreenInfoTextParser.currentDisplay(displayInfo) shouldBe expected
    }
    // endregion

    // region supportedDisplayMode()
    @Test
    fun `supportedDisplayMode reports Unknown for an empty list`() {
        ScreenInfoTextParser.supportedDisplayMode(emptyList()) shouldBe "Unknown"
    }

    @Test
    fun `supportedDisplayMode formats a single mode`() {
        val modes = listOf(DisplayMode(id = 0, refreshRate = 60, width = 1080, height = 1920))
        ScreenInfoTextParser.supportedDisplayMode(modes) shouldBe "(0) 1080 x 1920${lineSeparator}60Hz"
    }

    @Test
    fun `supportedDisplayMode joins multiple modes with a blank line between them`() {
        val modes = listOf(
            DisplayMode(id = 0, refreshRate = 60, width = 1080, height = 1920),
            DisplayMode(id = 1, refreshRate = 120, width = 1080, height = 1920),
        )
        val expected = "(0) 1080 x 1920${lineSeparator}60Hz$lineSeparator$lineSeparator(1) 1080 x 1920${lineSeparator}120Hz"
        ScreenInfoTextParser.supportedDisplayMode(modes) shouldBe expected
    }
    // endregion
}
