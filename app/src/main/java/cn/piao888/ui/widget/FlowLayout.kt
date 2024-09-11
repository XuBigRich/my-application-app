package cn.piao888.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import cn.piao888.R

class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    lateinit var allLines: MutableList<MutableList<View>>
    lateinit var lineHeights: MutableList<Int>
    var textView: TextView

    init {
        // 创建 textView 实例并添加到 FlowLayout 中
        textView = TextView(context)
        textView.apply {
            mText = "aaaaa"
            mPanel.textSize = 120f
        }
        addView(textView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        initMeasureParams()
        var lineHeight = 0
        var containerHeight = 0
        var lineWidth = 0
        var containerWidth = 0
        var lineView = mutableListOf<View>()
        var widthLimit = MeasureSpec.getSize(widthMeasureSpec)
        (0 until childCount).forEach {
            var childView = getChildAt(it)
            var childLayoutParams1 = childView.layoutParams
            //当前子视图宽度
            var childWidthMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight,
                childLayoutParams1.width
            )
            //当前子视图高度
            var childHeightMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingTop + paddingEnd,
                childLayoutParams1.height
            )
            //传入父视图可提供的大小 ,让子视图计算实际使用的视图大小
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)
            //子视图测量完后确认的大小
            lineWidth += childView.measuredWidth + 10

            if (lineWidth > widthLimit) {
                allLines.add(lineView)
                lineView = mutableListOf<View>()
                containerWidth = Integer.max(lineWidth, containerWidth)
                var lineHeightSize = lineHeight + 10
                lineHeights.add(lineHeightSize)
                containerHeight += lineHeightSize
                lineWidth = childView.measuredWidth
                lineHeight = childView.measuredHeight
            } else {
                lineHeight = Integer.max(lineHeight, childView.measuredHeight)
            }
            lineView.add(childView)
            if (it == childCount - 1) {
                allLines.add(lineView)
                lineHeights.add(lineHeight + 10)
            }
        }
        val realWidth = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            containerWidth
        }

        var realHeight =
            if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
                MeasureSpec.getSize(heightMeasureSpec)
            } else containerHeight
        setMeasuredDimension(realWidth, realHeight)
    }

    private fun initMeasureParams() {
        allLines = mutableListOf<MutableList<View>>()
        lineHeights = mutableListOf<Int>()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var curX = paddingLeft
        var curY = paddingTop
        (0 until allLines.size).forEach {
            allLines[it].forEach {
                var x1 = curX + it.measuredWidth
                var y1 = curY + it.measuredHeight
                it.layout(curX, curY, x1, y1)
                curX = x1 + 10
            }
            curX = paddingLeft
            curY += paddingTop + lineHeights.get(it) + 10
        }
    }
}