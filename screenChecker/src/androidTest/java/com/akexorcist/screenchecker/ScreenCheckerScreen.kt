package com.akexorcist.screenchecker

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KTextView

object ScreenCheckerScreen : KScreen<ScreenCheckerScreen>() {
    override val layoutId: Int = R.layout.activity_screen_checker
    override val viewClass: Class<*> = ScreenCheckerActivity::class.java

    val layoutSingleResolution = KView { withId(R.id.layoutSingleResolution) }
    val layoutMultiResolution = KView { withId(R.id.layoutMultiResolution) }

    val textViewMultiDeviceResolutionPx = KTextView { withId(R.id.textViewMultiDeviceResolutionPx) }
    val textViewMultiDeviceResolutionDp = KTextView { withId(R.id.textViewMultiDeviceResolutionDp) }
    val textViewMultiCurrentResolutionPx = KTextView { withId(R.id.textViewMultiCurrentResolutionPx) }
    val textViewMultiCurrentResolutionDp = KTextView { withId(R.id.textViewMultiCurrentResolutionDp) }
    val textViewMultiAppResolutionPx = KTextView { withId(R.id.textViewMultiAppResolutionPx) }
    val textViewMultiAppResolutionDp = KTextView { withId(R.id.textViewMultiAppResolutionDp) }

    val textViewDpi = KTextView { withId(R.id.textViewDpi) }
    val textViewCurrentDisplay = KTextView { withId(R.id.textViewCurrentDisplay) }
    val textViewSupportedDisplayMode = KTextView { withId(R.id.textViewSupportedDisplayMode) }
    val textViewRotation = KTextView { withId(R.id.textViewRotation) }
    val textViewSize = KTextView { withId(R.id.textViewSize) }
    val textViewDensity = KTextView { withId(R.id.textViewDensity) }
    val textViewLayout = KTextView { withId(R.id.textViewLayout) }
    val textViewUiMode = KTextView { withId(R.id.textViewUiMode) }
    val textViewColorMode = KTextView { withId(R.id.textViewColorMode) }
    val textViewHdrType = KTextView { withId(R.id.textViewHdrType) }
    val textViewMultitouch = KTextView { withId(R.id.textViewMultitouch) }
}
