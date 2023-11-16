package com.akexorcist.screenchecker

import android.hardware.display.DisplayManager
import android.os.Build
import androidx.annotation.RequiresApi

interface OnDisplayChangedListener : DisplayManager.DisplayListener {
    override fun onDisplayAdded(displayId: Int) {}

    override fun onDisplayRemoved(displayId: Int) {}
}
