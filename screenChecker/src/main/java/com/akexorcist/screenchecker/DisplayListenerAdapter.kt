package com.akexorcist.screenchecker

import android.hardware.display.DisplayManager
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
interface OnDisplayChangedListener : DisplayManager.DisplayListener {
    override fun onDisplayAdded(displayId: Int) {}

    override fun onDisplayRemoved(displayId: Int) {}
}
