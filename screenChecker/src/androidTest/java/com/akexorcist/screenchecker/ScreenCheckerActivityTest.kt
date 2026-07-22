package com.akexorcist.screenchecker

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenCheckerActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ScreenCheckerActivity::class.java)

    @Test
    fun resolutionLayoutMatchesTheCurrentApiLevel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onView(withId(R.id.layoutMultiResolution)).check(matches(isDisplayed()))
            onView(withId(R.id.layoutSingleResolution)).check(matches(not(isDisplayed())))
        } else {
            onView(withId(R.id.layoutSingleResolution)).check(matches(isDisplayed()))
            onView(withId(R.id.layoutMultiResolution)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun resolutionValuesAreDisplayedForTheActiveLayout() {
        val resolutionViewIds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listOf(
                R.id.textViewMultiDeviceResolutionPx,
                R.id.textViewMultiDeviceResolutionDp,
                R.id.textViewMultiCurrentResolutionPx,
                R.id.textViewMultiCurrentResolutionDp,
                R.id.textViewMultiAppResolutionPx,
                R.id.textViewMultiAppResolutionDp,
            )
        } else {
            listOf(
                R.id.textViewSingleDeviceResolutionPx,
                R.id.textViewSingleDeviceResolutionDp,
                R.id.textViewSingleAppResolutionPx,
                R.id.textViewSingleAppResolutionDp,
            )
        }

        resolutionViewIds.forEach { id ->
            onView(withId(id)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun allScreenInfoRowsAreDisplayed() {
        val infoViewIds = listOf(
            R.id.textViewDpi,
            R.id.textViewCurrentDisplay,
            R.id.textViewSupportedDisplayMode,
            R.id.textViewRotation,
            R.id.textViewSize,
            R.id.textViewDensity,
            R.id.textViewLayout,
            R.id.textViewUiMode,
            R.id.textViewColorMode,
            R.id.textViewHdrType,
            R.id.textViewMultitouch,
        )

        infoViewIds.forEach { id ->
            onView(withId(id)).check(matches(isDisplayed()))
        }
    }
}
