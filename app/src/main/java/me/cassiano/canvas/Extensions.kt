package me.cassiano.canvas

import android.content.Context
import android.graphics.Canvas
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension

fun Float.dp(context: Context): Float {
    val metrics = context.resources.displayMetrics
    return applyDimension(COMPLEX_UNIT_DIP, this, metrics)
}

fun Canvas.use(block: Canvas.() -> Unit) {
    save()
    block()
    restore()
}


fun FragmentManager.runTransactionAsync(block: FragmentTransaction.() -> Unit) {
    with(beginTransaction()) {
        block()
        commit()
    }
}