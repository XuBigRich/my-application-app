package cn.piao888.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import cn.piao888.R

class TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var mText: CharSequence?
    var textViewTextSize: Int = 15
    var mTextColor: Int
    var mTextLength: Int = 10
    val mTextBounds = Rect()
    var mPanel = Paint()

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.TextView)
        mText = array.getText(R.styleable.TextView_textViewText)
        mTextColor = array.getColor(
            R.styleable.TextView_textViewTextColor,
            ContextCompat.getColor(getContext(), R.color.black)
        )
        textViewTextSize =
            array.getDimensionPixelSize(R.styleable.TextView_textViewTextSize, textViewTextSize)
        mTextLength = array.getInt(R.styleable.TextView_textViewTextMaxLength, mTextLength)
        array.recycle()
        mPanel.isAntiAlias = true
        mPanel.setColor(mTextColor)
        mPanel.textSize = textViewTextSize.toFloat()
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        mText?.let {
            mPanel.getTextBounds(it, 0, it.length, mTextBounds)
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mTextBounds.width() + paddingLeft + paddingRight
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mTextBounds.height() + paddingTop + paddingBottom
        }

        setMeasuredDimension(widthSize, heightSize)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mText?.let {
            val x = paddingLeft.toFloat()
            val y = paddingTop.toFloat() + mTextBounds.height()
            canvas.drawText(it, 0, it.length, x, y, mPanel)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    fun setTextColor(color: Int) {
        mTextColor = color
        mPanel.color = mTextColor
        invalidate() // 请求重新绘制视图以反映颜色更改
    }

}