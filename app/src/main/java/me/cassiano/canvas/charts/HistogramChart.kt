package me.cassiano.canvas.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Shader
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.widget.ExploreByTouchHelper
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

data class Bar(
        val xLabel: CharSequence,
        val yLabel: CharSequence,
        val contentDescription: CharSequence,
        @Px val width: Float,
        @ColorInt val colorTint: Int,
        val shader: Shader? = null
)

class HistogramChart @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var bars: List<Bar> by Delegates.observable(emptyList()) { _, _, items ->
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {


    }

    private fun drawBar(bar: Bar) {

    }
}

class HistogramChartAccessibilityDelegate(
        view: HistogramChart
) : ExploreByTouchHelper(view) {


    override fun getVirtualViewAt(x: Float, y: Float): Int {

        return HOST_ID
    }

    override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPerformActionForVirtualView(
            virtualViewId: Int,
            action: Int,
            arguments: Bundle?
    ): Boolean = false

    override fun onPopulateNodeForVirtualView(
            virtualViewId: Int,
            node: AccessibilityNodeInfoCompat
    ) {

    }

}