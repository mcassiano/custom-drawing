package me.cassiano.canvas

import android.graphics.Canvas

fun Canvas.use(block: Canvas.() -> Unit) {
    save()
    block()
    restore()
}