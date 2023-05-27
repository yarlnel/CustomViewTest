package com.example.customviewtest.views.surface.fluppy

class FluppyBird {
    var x = 50
    var y = 500
    var isAlive = true
        private set

    var rotation = 0.0
    var millis = 0

    fun update(dy: Int) {
        y += dy
    }

    fun setStatus(status: Boolean) {
        isAlive = status
    }
}
