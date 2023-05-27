package com.example.customviewtest.views.surface.fluppy

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.view.SurfaceHolder
import com.example.customviewtest.R

class FluppyBirdRenderingThread(
    private val holder: SurfaceHolder,
    private val resources: Resources,
    private val bird: FluppyBird,
    private val pipeController: PipeController,
    private val height: Int,
    private val width: Int,
) : Thread() {

    var dy = 0

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 200f
    }

    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    private val birdBitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.drawable.bird
        )
    }
    private val bottomPipeBitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.drawable.pipe
        )
    }

    private val reverseTransformation = Matrix().apply {
        preScale(1.0f, -1.0f)
    }

    private val topPipeBitmap by lazy {
        Bitmap.createBitmap(
            bottomPipeBitmap,
            0,
            0,
            bottomPipeBitmap.width,
            bottomPipeBitmap.height,
            reverseTransformation,
            false
        )
    }

    private var lastRenderTime = 0L
    private var startedTime = 0L
    private var score = 0
    private var isRunning = false

    fun setRunning(isRunning: Boolean) {
       this@FluppyBirdRenderingThread.isRunning = isRunning
    }

    init {
        pipeController.afterPipeReset {
            if (bird.isAlive) {
                score++
            }
        }
    }

    override fun run() {
        startedTime = time()
        while (isRunning) {
            val elapsedTime = time() - lastRenderTime
            if (elapsedTime < Constants.RedrawTime) continue

            val millisFromStart = time() - startedTime
            if (millisFromStart >= 130 && millisFromStart % 2 == 0L) {
                dy += 3
            }

            bird.update(dy)
            pipeController.moveX()

            val canvas = holder.lockCanvas()
            canvas.render()
            holder.unlockCanvasAndPost(canvas)

            val birdRect = RectF(
                bird.x.toFloat(),
                bird.y.toFloat(),
                bird.x.toFloat() + birdBitmap.width,
                bird.y.toFloat() + birdBitmap.height
            )
            val topPipeRect = RectF(
                pipeController.x,
                pipeController.topY,
                pipeController.x + topPipeBitmap.width,
                pipeController.topY + topPipeBitmap.height
            )
            val bottomPipeRect = RectF(
                pipeController.x,
                pipeController.bottomY,
                pipeController.x + bottomPipeBitmap.width,
                height.toFloat()
            )

            val isStrikeTopPipe = birdRect.intersect(topPipeRect)
            val isStrikeBottomPipe = birdRect.intersect(bottomPipeRect)
            val isFall = bird.y > height

            if (isStrikeBottomPipe || isStrikeTopPipe || isFall) {
                bird.setStatus(false)
                //TODO
            }

            lastRenderTime = time()
        }
    }


    private fun Canvas.render() {
        drawColor(Color.WHITE)
        drawBitmap(
            topPipeBitmap,
            pipeController.x,
            pipeController.topY,
            bitmapPaint
        )

        drawBitmap(
            bottomPipeBitmap,
            pipeController.x,
            pipeController.bottomY,
            bitmapPaint
        )

        drawBitmap(
            birdBitmap,
            bird.x.toFloat(),
            bird.y.toFloat(),
            bitmapPaint
        )

        drawText(score.toString(), 5f, 200f, textPaint)
    }

    private fun time() = System.nanoTime() / 1_000_000

    private object Constants {
        const val RedrawTime = 16 // 16 ms for 60 frame rate
    }
}