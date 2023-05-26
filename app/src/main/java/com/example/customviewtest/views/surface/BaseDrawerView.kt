package com.example.customviewtest.views.surface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class BaseDrawerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    init {
        holder.addCallback(this@BaseDrawerView)
    }

    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.GREEN
    }

    private var path: Path = Path()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
            }

            MotionEvent.ACTION_UP -> {
                path.lineTo(event.x, event.y)
            }
        }

        val canvas = holder.lockCanvas()
        canvas.drawPath(path, linePaint)
        holder.unlockCanvasAndPost(canvas)

        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder) = with(holder.lockCanvas()) canvas@ {
        drawColoredButton(20f, 20f,  Color.RED)
        drawColoredButton(20f, Constants.ColoredButtonSideSize + 40f,  Color.GREEN)
        drawColoredButton(20f, (Constants.ColoredButtonSideSize * 2) + 60f,  Color.BLUE)
        holder.unlockCanvasAndPost(this@canvas)
    }


    private fun Canvas.drawColoredButton(left: Float, top: Float, color: Int) {
        drawRoundRect(
            left,
            top,
            left + Constants.ColoredButtonSideSize,
            top + Constants.ColoredButtonSideSize,
            Constants.ColoredButtonSideSize / 6,
            Constants.ColoredButtonSideSize / 6,
            Paint().apply {
                style = Paint.Style.FILL
                this.color = color
            }
        )
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
    }

    private object Constants {
        const val ColoredButtonSideSize = 100f
    }
}
