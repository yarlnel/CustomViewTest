package com.example.customviewtest.views.surface

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.min

class AnimationSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private val drawingThread = DrawingThread(holder, context.resources)

    init {
        holder.addCallback(this@AnimationSurfaceView)
    }

    // called when surface became visible
    override fun surfaceCreated(p0: SurfaceHolder) {
        drawingThread.setRunning(true)
        drawingThread.start()
    }


    // called when view changes own size
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    // called when view become invisible for user
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

class DrawingThread(
    private val holder: SurfaceHolder,
    private val resources: Resources
) : Thread() {

    private var isRunning = false
    private var lastRedrawTime = 0L
    private var startAnimationTime = 0L
    private var currentSec = 10L

    fun setRunning(isRunning: Boolean) {
        this@DrawingThread.isRunning = isRunning
    }

    private val ballPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val argbEvaluator = ArgbEvaluator()

    override fun run() {
        startAnimationTime = time()

        while (isRunning) {
            val elapsedTime = time() - lastRedrawTime
            if (elapsedTime < Constants.RedrawTime) continue

            val canvas = holder.lockCanvas() ?: continue
            canvas.draw()
            holder.unlockCanvasAndPost(canvas)

            currentSec = (time() - startAnimationTime) / 1_000
            lastRedrawTime = time()
        }
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        textSize = 40f
    }

    private val bigTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 140f
    }

    private var countDraw = 0
    private fun Canvas.draw() {
        countDraw++
        drawColor(Color.BLUE)
        val xCenter = width / 2f
        val yCenter = height / 2f


        val currentTimeFromStart = (time() - startAnimationTime).toFloat()
        val fraction = (currentTimeFromStart % Constants.AnimationTime) / Constants.AnimationTime
        // Compute how more percent of animation passed

        drawText(
            "Width: $width,Height: $height",
            10f,
            40f,
            textPaint
        )

        drawText(
            "onDraw: $countDraw, fraction: $fraction",
            10f,
            100f,
            textPaint
        )

        val color = argbEvaluator.evaluate(fraction, Color.BLUE, Color.WHITE) as Int
        ballPaint.color = color

        val maxBallSize = (min(width, height) / 2).toFloat()
        val ballSize = fraction * maxBallSize

        drawCircle(xCenter, yCenter, ballSize, ballPaint)

        if (fraction > 0.9f) {
            val currentSecText = currentSec.toString()
            val textWidth = bigTextPaint.measureText(currentSecText)
            val textX = xCenter - (textWidth / 2)
            val textY = yCenter + (bigTextPaint.textSize / 2)
            drawText(
                currentSecText,
                textX,
                textY,
                bigTextPaint
            )
        }
    }

    private fun time() = System.nanoTime() / 1_000_000

    private object Constants {
        const val RedrawTime = 10 // 10 ms
        const val AnimationTime = 1_000 // 1 sec
    }
}