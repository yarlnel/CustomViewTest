package com.example.customviewtest.views.surface.fluppy

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class FluppyBirdGameView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context), SurfaceHolder.Callback {

    private var drawingThread: FluppyBirdRenderingThread? = null

    private val bird = FluppyBird()

    private val pipeController by lazy {
        PipeController(width, height, resources)
    }

    init {
        holder.addCallback(this@FluppyBirdGameView)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (bird.isAlive) {
                drawingThread?.dy = -25
            }
        }
        return true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        drawingThread = FluppyBirdRenderingThread(
            holder,
            resources,
            bird,
            pipeController,
            height,
            width
        )
        drawingThread?.setRunning(true)
        drawingThread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        drawingThread?.setRunning(false)
        var retry = true
        while (retry) {
            try {
                drawingThread?.join()
                retry = false
            } catch (iex: InterruptedException) {}
        }
    }
}