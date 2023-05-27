package com.example.customviewtest.models


class StarsModel {
    var points = ArrayList<Point>()

    fun update(elapsedTime: Long) {
        for (i in 0..2) {
            points.add(
                Point(
                    random(-1f, 1f), random(-1f, 1f), Constants.InitialZCord,
                    -0x1000000 or (random(180f, 255f).toInt() shl 16) or (random(
                        180f,
                        255f
                    ).toInt() shl 8) or random(180f, 255f).toInt()
                )
            )
        }
        for (point in points) {
            point.z += elapsedTime * Constants.MotionSpeed
        }
        val iterator = points.iterator()
        while (iterator.hasNext()) {
            val point = iterator.next()
            if (point.z >= 0) {
                iterator.remove()
            }
        }
    }

    private fun random(from: Float, to: Float): Float {
        return (from + (to - from) * Math.random()).toFloat()
    }

    object Constants {
        var MotionSpeed = 0.00000000025f
        var InitialZCord = -3f
    }
}
