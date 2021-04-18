package com.akexorcist.screenchecker

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.akexorcist.screenchecker.databinding.ActivityScreenCheckerBinding

class ScreenCheckerActivity : Activity() {
    private val binding: ActivityScreenCheckerBinding by lazy { ActivityScreenCheckerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun setupView() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.layoutSingleResolution.visibility = View.GONE
            binding.layoutMultiResolution.visibility = View.VISIBLE
            binding.textViewMultiDeviceResolutionPx.text =
                ScreenInfoTextParser.resolutionPx(ScreenUtility.getDeviceResolutionPx(this))
            binding.textViewMultiDeviceResolutionDp.text =
                ScreenInfoTextParser.resolutionDp(ScreenUtility.getDeviceResolutionDp(this))
            binding.textViewMultiCurrentResolutionPx.text =
                ScreenInfoTextParser.resolutionPx(ScreenUtility.getCurrentResolutionPx(this))
            binding.textViewMultiCurrentResolutionDp.text =
                ScreenInfoTextParser.resolutionDp(ScreenUtility.getCurrentResolutionDp(this))
        } else {
            binding.layoutSingleResolution.visibility = View.VISIBLE
            binding.layoutMultiResolution.visibility = View.GONE
            binding.textViewSingleDeviceResolutionPx.text =
                ScreenInfoTextParser.resolutionPx(ScreenUtility.getCurrentResolutionPx(this))
            binding.textViewSingleDeviceResolutionDp.text =
                ScreenInfoTextParser.resolutionDp(ScreenUtility.getCurrentResolutionDp(this))
        }
        binding.textViewDpi.text = ScreenInfoTextParser.dpi(ScreenUtility.getDpi(this))
        binding.textViewSize.text = ScreenInfoTextParser.size(ScreenUtility.getSize(this))
        binding.textViewDensity.text = ScreenInfoTextParser.density(ScreenUtility.getDensity(this))
        binding.textViewLayout.text = ScreenInfoTextParser.layout(ScreenUtility.getLayout(this))
        binding.textViewUiMode.text = ScreenInfoTextParser.uiMode(ScreenUtility.getUiMode(this))
        binding.textViewColorMode.text = ScreenInfoTextParser.colorMode(ScreenUtility.getColorMode(this))
        binding.textViewHdrType.text = ScreenInfoTextParser.hdrType(ScreenUtility.getHdrType(this))
        binding.textViewMultitouch.text = ScreenInfoTextParser.multitouch(ScreenUtility.getMultitouch(this))
    }

    private val globalLayoutListener = OnGlobalLayoutListener {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.textViewMultiAppResolutionPx.text =
                ScreenInfoTextParser.resolutionPx(ScreenUtility.getAppResolutionPx(binding.root))
            binding.textViewMultiAppResolutionDp.text = ScreenInfoTextParser.resolutionDp(
                ScreenUtility.getAppResolutionDp(
                    this@ScreenCheckerActivity,
                    binding.root
                )
            )
        } else {
            binding.textViewSingleAppResolutionPx.text =
                ScreenInfoTextParser.resolutionPx(ScreenUtility.getAppResolutionPx(binding.root))
            binding.textViewSingleAppResolutionDp.text = ScreenInfoTextParser.resolutionDp(
                ScreenUtility.getAppResolutionDp(
                    this@ScreenCheckerActivity,
                    binding.root
                )
            )
        }
    }
}