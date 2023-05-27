package com.example.customviewtest.views.surface.fluppy

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.customviewtest.R
import kotlin.math.floor

class PipeController(
    private val height: Int,
    private val width: Int,
    resources: Resources
) {
    private var pipeHeight = 0f
    private var pipeWidth = 0f

    init {
        val pipe = BitmapFactory.decodeResource(resources, R.drawable.pipe)
        pipeHeight = pipe.height.toFloat()
        pipeWidth = pipe.width.toFloat()
    }

    private val gap = height * 0.5f
    private val deltaX = .014f * width

    var topY: Float = 0f
        private set

    var bottomY: Float = 0f
        private set

    var x: Float = 0f
        private set

    init {
        reset()
    }

    fun moveX() {
        x -= deltaX
    }

    fun reset() {
        x = width.toFloat()
        topY = computeTopY()
        bottomY = topY + pipeHeight + gap
    }

    private fun computeTopY() : Float =
        floor(Math.random() * (height / 2) + .3 * height - pipeHeight).toFloat()
}
// floor(Math.random() * (height / 2) + .3 * height).toInt()
