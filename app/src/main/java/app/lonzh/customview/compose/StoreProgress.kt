package app.lonzh.customview.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import app.lonzh.customview.R
import kotlin.math.atan

/**
 *
 * @ProjectName:    CustomView
 * @Description:    内存/存储进度
 * @Author:         Lisper
 * @CreateDate:     2021/7/12 6:09 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/12 6:09 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class StoreProgress : View {
    private var minViewHeight: Int = 0

    private var defaultColor: Int = 0

    private var progressColor: Int = 0

    private var progressTextSize: Float = 0f

    private var progressTextColor: Int = 0

    private var progressContent: String? = null

    private var progressContentTextSize: Float = 0f

    private var circleStrokeWidth: Float = 0f

    private var maxProgress: Int = 0

    private var progress: Int = 0

    private var radius: Float = 0f

    private lateinit var circlePaint: Paint

    private lateinit var paint: Paint

    private lateinit var textPaint: Paint

    private var contentBottomMargin: Float = 0f

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StoreProgress)

        defaultColor = typedArray.getColor(
            R.styleable.StoreProgress_StoreProgress_default_bg,
            ContextCompat.getColor(context, R.color.black20)
        )

        progressColor = typedArray.getColor(
            R.styleable.StoreProgress_StoreProgress_progress_bg,
            ContextCompat.getColor(context, android.R.color.holo_blue_light)
        )

        progressTextColor = typedArray.getColor(
            R.styleable.StoreProgress_StoreProgress_text_color,
            ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        )

        progressTextSize = typedArray.getDimension(
            R.styleable.StoreProgress_StoreProgress_text_size,
            resources.getDimension(R.dimen.text_30sp)
        )

        maxProgress = typedArray.getInt(
            R.styleable.StoreProgress_StoreProgress_max_progress,
            100
        )

        progress = typedArray.getInt(
            R.styleable.StoreProgress_StoreProgress,
            0
        )

        circleStrokeWidth = typedArray.getDimension(
            R.styleable.StoreProgress_StoreProgress_stroke_width,
            resources.getDimension(R.dimen.dp_6)
        )

        progressContent =
            typedArray.getString(R.styleable.StoreProgress_StoreProgress_content)

        if (progressContent == null) {
            progressContent = "StoreProgress"
        }

        progressContentTextSize = typedArray.getDimension(
            R.styleable.StoreProgress_StoreProgress_content_text_size,
            resources.getDimension(R.dimen.text_10sp)
        )

        contentBottomMargin = resources.getDimension(R.dimen.dp_6)

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

        paint = Paint().apply {
            color = progressTextColor
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
        }

        textPaint = Paint().apply {
            color = progressTextColor
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
        radius = ((w.coerceAtMost(h) - circleStrokeWidth) / 2).toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        //画文字
        val textRect = Rect()
        val progressText = progress * 100 / maxProgress
        textPaint.textSize = progressTextSize // 设置画笔在测量 绘制之前
        textPaint.getTextBounds("$progressText%", 0, "$progressText%".length, textRect)
        canvas.drawText(
            "$progressText%",
            -textRect.width().toFloat() / 2,
            textRect.height().toFloat() / 2,
            textPaint
        )
        val rect = Rect()
        textPaint.textSize = progressContentTextSize  // 设置画笔在测量 绘制之前
        textPaint.getTextBounds(progressContent, 0, progressContent!!.length, rect)
        canvas.drawText(
            progressContent!!,
            (-rect.width() / 2).toFloat(), radius - contentBottomMargin, textPaint
        )
        //画背景圆
        circlePaint.color = defaultColor
        val degrees =  Math.toDegrees(atan(((rect.width() / 2 + contentBottomMargin) / (radius - rect.height() - contentBottomMargin)).toDouble()))
        val circleRecF = RectF(-radius, -radius, radius, radius)
        canvas.drawArc(circleRecF, (90f + degrees).toFloat(), ((360 - 2 * degrees).toFloat()), false, circlePaint)

        //画进度
        circlePaint.color = progressColor
        canvas.drawArc(circleRecF, (90f + degrees).toFloat(),
            ((360 - 2 * degrees) * progress / maxProgress).toFloat(), false, circlePaint)
        canvas.restore()
    }

    fun setStoreProgress(p: Int) {
        progress = p
        invalidate()
    }

    fun getStoreProgress(): Int {
        return progress
    }

    fun getMaxProgress(): Int {
        return maxProgress
    }

    fun setMaxProgress(max: Int) {
        maxProgress = max
    }
}