package com.akexorcist.screenchecker

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import kotlinx.android.synthetic.main.activity_main.*

class ScreenCheckerActivity : Activity() {
    private val rootView: View by lazy { window.decorView.findViewById<View>(android.R.id.content) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun setupView() {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutSingleResolution.visibility = View.GONE
            layoutMultiResolution.visibility = View.VISIBLE
            textViewMultiDeviceResolutionPx.text = ScreenInfoTextParser.resolutionPx(ScreenUtility.getDeviceResolutionPx(this))
            textViewMultiDeviceResolutionDp.text = ScreenInfoTextParser.resolutionDp(ScreenUtility.getDeviceResolutionDp(this))
            textViewMultiCurrentResolutionPx.text = ScreenInfoTextParser.resolutionPx(ScreenUtility.getCurrentResolutionPx(this))
            textViewMultiCurrentResolutionDp.text = ScreenInfoTextParser.resolutionDp(ScreenUtility.getCurrentResolutionDp(this))
        } else {
            layoutSingleResolution.visibility = View.VISIBLE
            layoutMultiResolution.visibility = View.GONE
            textViewSingleDeviceResolutionPx.text = ScreenInfoTextParser.resolutionPx(ScreenUtility.getCurrentResolutionPx(this))
            textViewSingleDeviceResolutionDp.text = ScreenInfoTextParser.resolutionDp(ScreenUtility.getCurrentResolutionDp(this))
        }
        textViewDpi.text = ScreenInfoTextParser.dpi(ScreenUtility.getDpi(this))
        textViewSize.text = ScreenInfoTextParser.size(ScreenUtility.getSize(this))
        textViewDensity.text = ScreenInfoTextParser.density(ScreenUtility.getDensity(this))
        textViewLayout.text = ScreenInfoTextParser.layout(ScreenUtility.getLayout(this))
        textViewUiMode.text = ScreenInfoTextParser.uiMode(ScreenUtility.getUiMode(this))
        textViewColorMode.text = ScreenInfoTextParser.colorMode(ScreenUtility.getColorMode(this))
        textViewMultitouch.text = ScreenInfoTextParser.multitouch(ScreenUtility.getMultitouch(this))
    }

    private val globalLayoutListener = OnGlobalLayoutListener {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewMultiAppResolutionPx.text = ScreenInfoTextParser.resolutionPx(ScreenUtility.getAppResolutionPx(rootView))
            textViewMultiAppResolutionDp.text = ScreenInfoTextParser.resolutionDp(ScreenUtility.getAppResolutionDp(this@ScreenCheckerActivity, rootView))
        } else {
            textViewSingleAppResolutionPx.text = ScreenInfoTextParser.resolutionPx(ScreenUtility.getAppResolutionPx(rootView))
            textViewSingleAppResolutionDp.text = ScreenInfoTextParser.resolutionDp(ScreenUtility.getAppResolutionDp(this@ScreenCheckerActivity, rootView))
        }
    }
}