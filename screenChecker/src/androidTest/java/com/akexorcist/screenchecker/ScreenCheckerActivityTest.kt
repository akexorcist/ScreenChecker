package com.akexorcist.screenchecker

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class ScreenCheckerActivityTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<ScreenCheckerActivity>()

    @Test
    fun screenCheckerActivityDisplaysAllInfo() = run {
        step("Multi-resolution layout is displayed, single-resolution layout is not") {
            ScreenCheckerScreen {
                layoutMultiResolution.isDisplayed()
                layoutSingleResolution.isNotDisplayed()
            }
        }

        step("Device, current and app resolution values are displayed") {
            ScreenCheckerScreen {
                textViewMultiDeviceResolutionPx.isDisplayed()
                textViewMultiDeviceResolutionDp.isDisplayed()
                textViewMultiCurrentResolutionPx.isDisplayed()
                textViewMultiCurrentResolutionDp.isDisplayed()
                textViewMultiAppResolutionPx.isDisplayed()
                textViewMultiAppResolutionDp.isDisplayed()
            }
        }

        step("All screen info rows are displayed") {
            ScreenCheckerScreen {
                textViewDpi.isDisplayed()
                textViewCurrentDisplay.isDisplayed()
                textViewSupportedDisplayMode.isDisplayed()
                textViewRotation.isDisplayed()
                textViewSize.isDisplayed()
                textViewDensity.isDisplayed()
                textViewLayout.isDisplayed()
                textViewUiMode.isDisplayed()
                textViewColorMode.isDisplayed()
                textViewHdrType.isDisplayed()
                textViewMultitouch.isDisplayed()
            }
        }
    }
}
