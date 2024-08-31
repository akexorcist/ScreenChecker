package com.akexorcist.screenchecker

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.akexorcist.screenchecker.databinding.ActivityScreenCheckerBinding

class ScreenCheckerActivity : ComponentActivity() {
    private val binding: ActivityScreenCheckerBinding by lazy { ActivityScreenCheckerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                rightMargin = insets.right
                bottomMargin = insets.bottom
            }
            WindowInsetsCompat.CONSUMED
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        updateScreenInfo()
    }

    private val globalLayoutListener = OnGlobalLayoutListener {
        updateAppResolution()
    }

    override fun onStart() {
        super.onStart()
        registerDisplayListener()
    }

    override fun onStop() {
        super.onStop()
        unregisterDisplayListener()
    }

    private fun registerDisplayListener() {
        val manager: DisplayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        manager.registerDisplayListener(displayListener, Handler(Looper.getMainLooper()))
    }

    private fun unregisterDisplayListener() {
        val manager: DisplayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        manager.unregisterDisplayListener(displayListener)
    }

    private val displayListener = object : OnDisplayChangedListener {
        override fun onDisplayChanged(displayId: Int) {
            updateScreenInfo()
        }
    }

    private fun updateScreenInfo() {
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
        val displayInfo = ScreenUtility.getCurrentDisplay(this)
        binding.textViewCurrentDisplay.text = ScreenInfoTextParser.currentDisplay(displayInfo)
        binding.textViewSupportedDisplayMode.text = ScreenInfoTextParser.supportedDisplayMode(ScreenUtility.getSupportedDisplayMode(this))
        binding.textViewDpi.text = ScreenInfoTextParser.dpi(ScreenUtility.getDensity(this))
        binding.textViewSize.text = ScreenInfoTextParser.size(ScreenUtility.getSize(this))
        binding.textViewDensity.text = ScreenInfoTextParser.density(ScreenUtility.getDensity(this))
        binding.textViewRotation.text = ScreenInfoTextParser.rotation(displayInfo.rotation)
        binding.textViewLayout.text = ScreenInfoTextParser.layout(ScreenUtility.getLayout(this))
        binding.textViewUiMode.text = ScreenInfoTextParser.uiMode(ScreenUtility.getUiMode(this))
        binding.textViewColorMode.text = ScreenInfoTextParser.colorMode(ScreenUtility.getColorMode(this))
        binding.textViewHdrType.text = ScreenInfoTextParser.hdrType(ScreenUtility.getHdrType(this))
        binding.textViewMultitouch.text = ScreenInfoTextParser.multitouch(ScreenUtility.getMultitouch(this))
    }

    private fun updateAppResolution() {
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