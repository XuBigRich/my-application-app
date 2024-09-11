package cn.piao888.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import cn.piao888.R

class QQStepView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var borderWidth = 100
    private var innerColor: Int = ContextCompat.getColor(getContext(), R.color.red)
    private var outerColor: Int = ContextCompat.getColor(getContext(), R.color.purple_700)
    private var stepTextColor: Int = ContextCompat.getColor(getContext(), R.color.purple_200)
    private var stepTextSize = 20
    private var mOutPaint: Paint
    private var mInnerPaint: Paint
    private var stepTextPaint: Paint
    public var mCurrentStep: Int = 20000
        get() = field
        set(value) {
            field = value
            invalidate()
        }
    private var mMaxStep: Int = 99999

    init {
        //1.分析效果
        //2.确定自定义属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        array.apply {
            borderWidth = getDimensionPixelSize(R.styleable.TextView_textViewTextSize, borderWidth)
            innerColor = getColor(R.styleable.QQStepView_innerColor, innerColor)
            outerColor = getColor(R.styleable.QQStepView_outerColor, outerColor)
            stepTextColor = getColor(R.styleable.QQStepView_stepTextColor, stepTextColor)
            stepTextSize = getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, stepTextSize)
        }
        mOutPaint = Paint()
        mOutPaint.setColor(outerColor)
        mOutPaint.isAntiAlias = true
        mOutPaint.strokeWidth = borderWidth.toFloat()
        mOutPaint.style = Paint.Style.STROKE

        mInnerPaint = Paint()
        mInnerPaint.setColor(innerColor)
        mInnerPaint.isAntiAlias = true
        mInnerPaint.strokeWidth = borderWidth.toFloat()
        mInnerPaint.style = Paint.Style.STROKE
        array.recycle()

        stepTextPaint = Paint()
        stepTextPaint.isAntiAlias = true
        stepTextPaint.setColor(stepTextColor)
        stepTextPaint.textSize = stepTextSize.toFloat()
        //3.在布局中使用
        //4.在自定义view中获取自定义
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
        }
        setMeasuredDimension(
            minOf(widthSize, heightSize), minOf(widthSize, heightSize)
        )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画外圆弧
        val rectF = RectF(
            0f + borderWidth / 2,
            0f + borderWidth / 2,
            width.toFloat() - borderWidth / 2,
            height.toFloat() - borderWidth / 2
        )
        canvas.drawArc(rectF, 135f, 270f, false, mOutPaint)
        //画内圆弧
        val sweepAngle = mCurrentStep.toFloat() / mMaxStep
        canvas.drawArc(rectF, 135f, 270f * sweepAngle, false, mInnerPaint)
        //画文字
//        计算文字长度
        val mTextBounds = Rect()
        var mCurrentStepString = mCurrentStep.toString()
        stepTextPaint.getTextBounds(mCurrentStepString, 0, mCurrentStepString.length, mTextBounds)
        var textHeight = mTextBounds.height()
        var textWidth = mTextBounds.width().toFloat()
        var fontMetrics = stepTextPaint.getFontMetrics()
        var top = fontMetrics.top
        var bottom = fontMetrics.bottom
        //这里是计算距离 不是计算坐标 ,找到 中线与基线的距离 中线在上基线在下为正、中线在下基线在上为负数
        var baseline = (bottom - top) / 2 - bottom
        var x = width.toFloat().minus(textWidth).div(2)
        //找到视图的中心位置,减去文字所占用大小,得到文字在视图中的  中线,
        var y = height.toFloat().minus(textHeight).div(2)
        canvas.drawText(mCurrentStep.toString(), x, y + baseline, stepTextPaint)
    }

}