package app.lonzh.customview.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import app.lonzh.customview.R

/**
 *
 * @ProjectName:    CustomView
 * @Description:    TextView绘制背景，并且在中间加一条横线
 * @Author:         Lisper
 * @CreateDate:     2021/7/12 11:31 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/12 11:31 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CustomTextView : AppCompatTextView {
    private var bgColor: Int = 0

    private var lineColor: Int = 0

    private var lineHeight: Float = 0f

    private lateinit var bgPaint: Paint

    private lateinit var linePaint: Paint

    constructor(context: Context) : super(context, null) {
        initialize(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        initialize(context, attrs, 0)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleInt: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        bgColor = typedArray.getColor(
            R.styleable.CustomTextView_customTextView_bg,
            ContextCompat.getColor(context, R.color.black10)
        )
        lineColor = typedArray.getColor(
            R.styleable.CustomTextView_customTextView_line_color,
            ContextCompat.getColor(context, R.color.black30)
        )
        lineHeight = typedArray.getDimension(
            R.styleable.CustomTextView_customTextView_line_height,
            resources.getDimension(R.dimen.dp_1)
        )
        typedArray.recycle()

        bgPaint = Paint().apply {
            color = bgColor
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
            style = Paint.Style.FILL
        }

        linePaint = Paint().apply {
            color = lineColor
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
            style = Paint.Style.FILL
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val rect = Rect(0, 0, width, height)
        canvas.drawRect(rect, bgPaint)
        super.onDraw(canvas)
        val rectFLine = RectF(0f,  (height - lineHeight) /2, width.toFloat(), (height + lineHeight)/2)
        canvas.drawRect(rectFLine, linePaint)
    }
}