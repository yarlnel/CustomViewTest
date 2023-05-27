package com.example.customviewtest.views.surface.stars

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import com.example.customviewtest.models.StarsModel
import kotlin.math.abs
import kotlin.math.roundToInt


class StarsDrawingThread(
    private val holder: SurfaceHolder
) : Thread() {

    private var isRunning = false
    private var lastTime = 0L

    fun setRunning(isRunning: Boolean) {
        this@StarsDrawingThread.isRunning = isRunning
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 3f
        style = Paint.Style.STROKE
    }

    private val starsModel = StarsModel()

    override fun run() {
        lastTime = System.nanoTime()

        while (isRunning) {
            val timeElapsed = System.nanoTime() - lastTime
            lastTime = System.nanoTime()
            starsModel.update(timeElapsed)
            val canvas = holder.lockCanvas()
            canvas.draw()
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun Canvas.draw() {
        drawColor(Color.BLACK)

        for (point in starsModel.points) {
            val sx = width / 2f + width / 2f * point.x / point.z
            val sy = height / 2f + height / 2f * point.y / point.z
            val isx = sx.roundToInt()
            val isy = sy.roundToInt()
            if (isx in 0 until width && isy < height && isy >= 0) {
                val colorGain =
                    (255 + (point.z * (255 / abs(StarsModel.Constants.InitialZCord)))) / 255f
                val colorR = point.color and 0xff0000 shr 16
                val colorG = point.color and 0xff00 shr 8
                val colorB = point.color and 0xff

                paint.color = (
                    -0x1000000
                    or ((colorR * colorGain).toInt() shl 16)
                    or ((colorG * colorGain).toInt() shl 8)
                    or (colorB * colorGain).toInt()
                )

                drawPoint(isx.toFloat(), isy.toFloat(), paint)
            }
        }
    }
}