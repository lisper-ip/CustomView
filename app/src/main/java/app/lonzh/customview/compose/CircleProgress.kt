package app.lonzh.customview.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import app.lonzh.customview.R

/**
 *
 * @ProjectName:    CustomView
 * @Description:    圆环进度
 * @Author:         Lisper
 * @CreateDate:     2021/7/12 4:21 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/12 4:21 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CircleProgress : View {

    private var minViewHeight: Int = 0

    private var defaultColor: Int = 0

    private var progressColor: Int = 0

    private var progressTextSize: Float = 0f

    private var progressTextColor: Int = 0

    private var circleStrokeWidth: Float = 0f

    private var maxProgress: Int = 0

    private var progress: Int = 0

    private var radius: Float = 0f

    private lateinit var circlePaint: Paint

    private lateinit var textPaint: Paint

    constructor(context: Context) : super(context, null) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyInt: Int) : super(
        context,
        attrs,
        defStyInt,
        0
    ) {
        init(context, attrs, defStyInt)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyInt: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress)

        defaultColor = typedArray.getColor(
            R.styleable.CircleProgress_CircleProgress_default_bg,
            ContextCompat.getColor(context, R.color.black20)
        )

        progressColor = typedArray.getColor(
            R.styleable.CircleProgress_CircleProgress_progress_bg,
            ContextCompat.getColor(context, android.R.color.holo_blue_light)
        )

        progressTextColor = typedArray.getColor(
            R.styleable.CircleProgress_CircleProgress_text_color,
            ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        )

        progressTextSize = typedArray.getDimension(
            R.styleable.CircleProgress_CircleProgress_text_size,
            resources.getDimension(R.dimen.text_12sp)
        )

        maxProgress = typedArray.getInt(
            R.styleable.CircleProgress_CircleProgress_max_progress,
            100
        )

        progress = typedArray.getInt(
            R.styleable.CircleProgress_CircleProgress,
            0
        )

        circleStrokeWidth = typedArray.getDimension(
            R.styleable.CircleProgress_CircleProgress_stroke_width,
            resources.getDimension(R.dimen.dp_6)
        )
        minViewHeight = resources.getDimension(R.dimen.dp_40).toInt()

        typedArray.recycle()

        circlePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = circleStrokeWidth
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
            //圆角或者方角 默认方角
            strokeCap = Paint.Cap.ROUND
        }

        textPaint = Paint().apply {
            color = progressTextColor
            textSize = progressTextSize
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minViewHeight, minViewHeight)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(heightSize, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, widthSize)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (w.coerceAtMost(h) - circleStrokeWidth - paddingLeft - paddingBottom) / 2
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()

        //画背景圆
        circlePaint.color = defaultColor
        canvas.drawCircle((width / 2).toFloat(), (height /2).toFloat(), radius, circlePaint)
        canvas.translate((width / 2).toFloat(), (height /2).toFloat())

        val rectF = RectF(- radius, -radius, radius, radius)
        circlePaint.color = progressColor
        val sweepAngle = 360 * progress * 1.0f / maxProgress
        canvas.drawArc(rectF, 0f, sweepAngle, false, circlePaint)
        //画文字
        val textRect = Rect()
        val progressText = progress * 100 / maxProgress
        textPaint.getTextBounds("$progressText%", 0, "$progressText%".length, textRect)
        canvas.drawText("$progressText%", -textRect.width().toFloat() / 2, textRect.height().toFloat() / 2, textPaint)
        canvas.restore()
    }

    fun setCircleProgress(p: Int){
        progress = p
        invalidate()
    }

    fun getCircleProgress(): Int{
        return progress
    }

    fun getMaxProgress(): Int{
        return maxProgress
    }

    fun setMaxProgress(max: Int){
        maxProgress = max
    }
}