package me.cassiano.canvas.charts

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.view.ViewCompat
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v4.widget.ExploreByTouchHelper
import android.util.AttributeSet
import android.view.View
import me.cassiano.canvas.dp
import me.cassiano.canvas.use
import kotlin.math.roundToInt
import kotlin.properties.Delegates.observable

private const val INITIAL_VIRTUAL_VIEWS_CAPACITY = 10

data class Bar(
        val xLabel: CharSequence,
        val yLabel: CharSequence,
        val yValue: Float,
        val contentDescription: CharSequence,
        @ColorInt val colorTint: Int,
        val shader: Shader? = null
)

data class VirtualView(
        var id: Int = View.NO_ID,
        var contentDescription: CharSequence? = null,
        val rect: Rect = Rect()
)

class HistogramChart @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val accessibilityDelegate =
            HistogramChartAccessibilityDelegate(this).also {
                ViewCompat.setAccessibilityDelegate(this, it)
            }

    private val clipPath = Path()
    private val barWidth = 20f.dp(context)
    private val barSpacing = 5f.dp(context)
    private val barRadius = 4f.dp(context)

    private val barPaint = Paint(ANTI_ALIAS_FLAG).apply {
        strokeWidth = barWidth
    }

    private val virtualViews = ArrayList<VirtualView>(INITIAL_VIRTUAL_VIEWS_CAPACITY)

    var bars: List<Bar> by observable(emptyList()) { _, _, items ->
        prepareVirtualViews(items)
        invalidate()
    }

    override fun onLayout(
            changed: Boolean,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            clipPath.reset()
            clipPath.addRect(
                    barSpacing * 3,
                    top + barSpacing * 3,
                    right - barSpacing * 3,
                    bottom - barSpacing * 3,
                    Path.Direction.CW
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.use {
            clipPath(clipPath)
            val clippingTranslate = barSpacing * 3

            canvas.use {
                translate(clippingTranslate, 0f)
                bars.forEachIndexed { index, bar ->
                    val view = virtualViews[index]
                    val left = clippingTranslate + index * (barSpacing + barWidth)
                    val top = height - bar.yValue
                    val right = left + barWidth
                    val bottom = height
                    view.rect.set(left.roundToInt(), top.roundToInt(), right.roundToInt(), bottom)
                    canvas.translate(if (index == 0) 0f else barWidth + barSpacing, 0f)
                    drawBar(canvas, bar)
                }

            }
        }

        accessibilityDelegate.invalidateRoot()
    }

    private fun drawBar(canvas: Canvas, bar: Bar) {
        val oldPaintColor = barPaint.color
        barPaint.color = bar.colorTint
        canvas.drawRoundRect(
                0f,
                height.toFloat(),
                barWidth,
                height - bar.yValue,
                barRadius,
                barRadius,
                barPaint
        )
        barPaint.color = oldPaintColor
    }

    private fun prepareVirtualViews(bars: List<Bar>) {
        if (bars.size <= virtualViews.size) {
            updateAccessibilityDelegate(bars, bars.size)
        } else {
            val extraSpace = bars.size - virtualViews.size
            virtualViews.ensureCapacity(bars.size)
            repeat(extraSpace) { virtualViews.add(VirtualView()) }
            updateAccessibilityDelegate(bars, bars.size)
        }
    }

    private fun updateAccessibilityDelegate(bars: List<Bar>, elements: Int) {
        bars.forEachIndexed { index, bar ->
            val view = virtualViews[index]
            view.id = index
            view.contentDescription = bar.contentDescription
        }
        accessibilityDelegate.virtualViews = virtualViews.subList(0, elements)
    }
}

internal class HistogramChartAccessibilityDelegate(
        view: HistogramChart
) : ExploreByTouchHelper(view) {

    var virtualViews: List<VirtualView> = emptyList()

    override fun getVirtualViewAt(x: Float, y: Float): Int {
        val view = virtualViews.find { it.rect.contains(x.toInt(), y.toInt()) }
        return view?.id ?: HOST_ID
    }

    override fun getVisibleVirtualViews(
            virtualViewIds: MutableList<Int>
    ) {
        virtualViews.forEach { virtualViewIds.add(it.id) }
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
        val view = virtualViews.find { it.id == virtualViewId }
        view?.let {
            node.setBoundsInParent(it.rect)
            node.contentDescription = it.contentDescription
        }
    }

}