package com.example.customviewtest.views.surface.stars

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class StarsSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context), SurfaceHolder.Callback {

    private val drawingThread = StarsDrawingThread(holder)

    init {
        holder.addCallback(this@StarsSurfaceView)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        drawingThread.setRunning(true)
        drawingThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        drawingThread.setRunning(false)
        var retry = true
        while (retry) {
            try {
                drawingThread.join()
                retry = false
            } catch (ex: InterruptedException) {}
        }
    }
}