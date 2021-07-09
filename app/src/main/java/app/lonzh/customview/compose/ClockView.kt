package app.lonzh.customview.compose

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import app.lonzh.customview.R
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.TimeUtils
import java.util.*

/**
 *
 * @ProjectName:    CustomView
 * @Description:    钟表自定义（主要复习基础）
 * @Author:         Lisper
 * @CreateDate:     2021/7/7 3:53 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/7 3:53 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ClockView : View {
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    //长刻度的长度
    private var longScaleLength: Float = 0f

    //短刻度的长度
    private var shortScaleLength: Float = 0f

    //文字距离长刻度的距离
    private var textMargin: Float = 0f

    //表盘边距
    private var clockMargin: Int = 0

    //表盘颜色
    private var clockColor: Int = 0

    //数字大小
    private var numTextSize: Float = 0f

    //中间外园的半径
    private var outCircleRadius = 0f

    //中间内园的半径
    private var inCircleRadius = 0f

    //表盘半径
    private var radius: Float = 0f

    //日期颜色
    private var dateColor: Int = 0

    //日期字体大小
    private var dataTextSize: Float = 0f

    //时针颜色
    private var hourColor: Int = 0

    //分针颜色
    private var minuteColor: Int = 0

    //秒针颜色
    private var secondColor: Int = 0

    //表盘
    private lateinit var paint: Paint

    //内圆(外)
    private lateinit var outPaint: Paint

    //内圆(内)
    private lateinit var inPaint: Paint

    //数字
    private lateinit var textPaint: Paint

    //日期
    private lateinit var datePaint: Paint

    //时针
    private lateinit var hourPaint: Paint

    //分针
    private lateinit var minutePaint: Paint

    //秒针
    private lateinit var secondPaint: Paint

    //时针宽度
    private var hourLength: Float = 0f
    private var hourMargin: Float = 0f

    //分针宽度
    private var minuteLength: Float = 0f
    private var minuteMargin: Float = 0f

    //秒针宽度
    private var secondLength: Float = 0f
    private var secondMargin: Float = 0f

    //箭头的长度
    private var arrowLength: Float = 0f

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
        screenWidth = ScreenUtils.getScreenWidth()
        screenHeight = ScreenUtils.getScreenHeight()

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockView)
        clockMargin = typedArray.getDimension(
            R.styleable.ClockView_clock_margin,
            resources.getDimension(R.dimen.dp_4)
        ).toInt()

        clockColor = typedArray.getColor(
            R.styleable.ClockView_clock_color,
            ContextCompat.getColor(context, R.color.black)
        )

        dateColor = typedArray.getColor(
            R.styleable.ClockView_clock_date_text_color,
            ContextCompat.getColor(context, R.color.black)
        )

        dataTextSize = typedArray.getDimension(
            R.styleable.ClockView_clock_date_text_size,
            resources.getDimension(R.dimen.text_10sp)
        )

        numTextSize = typedArray.getDimension(
            R.styleable.ClockView_clock_num_size,
            resources.getDimension(R.dimen.text_10sp)
        )

        outCircleRadius = typedArray.getDimension(
            R.styleable.ClockView_clock_out_circle_radius,
            resources.getDimension(R.dimen.dp_16)
        )

        inCircleRadius = typedArray.getDimension(
            R.styleable.ClockView_clock_in_circle_radius,
            resources.getDimension(R.dimen.dp_10)
        )

        hourColor = typedArray.getColor(
            R.styleable.ClockView_clock_hour_color,
            ContextCompat.getColor(context, R.color.black)
        )

        minuteColor = typedArray.getColor(
            R.styleable.ClockView_clock_minute_color,
            ContextCompat.getColor(context, R.color.black)
        )

        secondColor = typedArray.getColor(
            R.styleable.ClockView_clock_second_color,
            ContextCompat.getColor(context, R.color.black)
        )

        typedArray.recycle()

        longScaleLength = resources.getDimension(R.dimen.dp_8)
        shortScaleLength = resources.getDimension(R.dimen.dp_4)
        textMargin = resources.getDimension(R.dimen.dp_4)

        hourLength = resources.getDimension(R.dimen.dp_4)
        hourMargin = resources.getDimension(R.dimen.dp_40)

        minuteLength = resources.getDimension(R.dimen.dp_3)
        minuteMargin = resources.getDimension(R.dimen.dp_20)

        secondLength = resources.getDimension(R.dimen.dp_2)
        secondMargin = resources.getDimension(R.dimen.dp_1)

        arrowLength = resources.getDimension(R.dimen.dp_10)

        initPaint(context)
    }

    private fun initPaint(context: Context) {
        paint = Paint().apply {
            color = clockColor
            isAntiAlias = true
            strokeWidth = context.resources.getDimension(R.dimen.dp_2)
            style = Paint.Style.STROKE
        }

        outPaint = Paint().apply {
            color = clockColor
            isAntiAlias = true
            style = Paint.Style.FILL
            alpha = 60
            isDither = true
        }

        inPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.black)
            isAntiAlias = true
            style = Paint.Style.FILL
            isDither = true
        }

        hourPaint = Paint().apply {
            color = hourColor
            isAntiAlias = true
            style = Paint.Style.FILL
            isDither = true
        }

        minutePaint = Paint().apply {
            color = minuteColor
            isAntiAlias = true
            style = Paint.Style.FILL
            isDither = true
        }

        secondPaint = Paint().apply {
            color = secondColor
            isAntiAlias = true
            style = Paint.Style.FILL
            isDither = true
        }

        textPaint = Paint().apply {
            color = clockColor
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = numTextSize
            isDither = true
            isSubpixelText = true
            typeface = Typeface.DEFAULT_BOLD
        }

        datePaint = Paint().apply {
            color = dateColor
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = dataTextSize
            isDither = true
            isSubpixelText = true
            typeface = Typeface.DEFAULT_BOLD
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else {
            screenWidth
        }
        val height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else {
            screenHeight / 4
        }
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas) {
        //获取圆的半径
        radius = if (width > height) {
            (height / 2 - clockMargin).toFloat()
        } else {
            (height / 2 - clockMargin).toFloat()
        }
        canvas.save()
        //画大圆
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        //画刻度和数字
        drawNumberAndScale(canvas)
        //画星期
        drawWeek(canvas)
        //画日期
        drawDate(canvas)
        //画指针
        drawHand(canvas)
        //画内部(外)圆
        canvas.drawCircle(0f, 0f, outCircleRadius, outPaint)
        //画内部(内)圆
        canvas.drawCircle(0f, 0f, inCircleRadius, inPaint)
        canvas.restore()
        //跳秒处理
        postInvalidateDelayed(1000 - System.currentTimeMillis() % 1000)
    }

    private fun drawDate(canvas: Canvas) {
        canvas.save()
        canvas.translate(0f, radius / 3)
        val date = TimeUtils.date2String(Date(), "yyyy/MM/dd")
        val dateRect = Rect()
        textPaint.getTextBounds(date, 0, date.length, dateRect)
        canvas.drawText(date, 0f, ((dateRect.bottom - dateRect.top) / 2).toFloat(), datePaint)
        canvas.restore()
    }

    private fun drawWeek(canvas: Canvas) {
        canvas.save()
        canvas.translate(0f, -radius / 2)
        val week = TimeUtils.getChineseWeek(Date())
        val weekRect = Rect()
        textPaint.getTextBounds(week, 0, week.length, weekRect)
        canvas.drawText(week, 0f, ((weekRect.bottom - weekRect.top) / 2).toFloat(), datePaint)
        canvas.restore()
    }

    private fun drawNumberAndScale(canvas: Canvas) {
        //画布移动到中心点
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        canvas.save()

        for (index in 0..60) {
            if (index % 5 == 0) {
                //画长条
                canvas.drawLine(0f, -radius + longScaleLength, 0f, -radius, paint)
                //画文字
                canvas.save()
                val number = if (index == 0) {
                    12
                } else {
                    index / 5
                }
                val rect = Rect()
                textPaint.getTextBounds(number.toString(), 0, number.toString().length, rect)
                canvas.translate(
                    0f,
                    -radius + longScaleLength + textMargin * 3
                )
                canvas.rotate((-6 * index).toFloat())
                canvas.drawText(
                    number.toString(), 0f,
                    ((rect.bottom - rect.top) / 2).toFloat(), textPaint
                )
                canvas.restore()
            } else {
                //画短条
                canvas.drawLine(0f, -radius + shortScaleLength, 0f, -radius, paint)
            }
            canvas.rotate(6f)
        }
        canvas.restore()
    }

    private fun drawHand(canvas: Canvas) {
        val hour = TimeUtils.getValueByCalendarField(Calendar.HOUR)
        val minute = TimeUtils.getValueByCalendarField(Calendar.MINUTE)
        val second = TimeUtils.getValueByCalendarField(Calendar.SECOND)

        var hourRate = 360 * hour * 1.0f / 12
        val minuteRate = 360 * minute * 1.0f / 60
        hourRate += 30 * minuteRate * 1.0f / 360
        val secondRate = 360 * second * 1.0f / 60

        //画小时指针
        val marginHour = radius - longScaleLength - hourMargin - arrowLength
        val hourRectF = RectF(- hourLength, - marginHour, hourLength, 0f)
        canvas.save()
        canvas.rotate(0f)
        canvas.drawRoundRect(hourRectF, 2f, 2f, hourPaint)
        //画箭头
        val hourPath = Path()
        hourPath.moveTo(0f, - (marginHour + arrowLength))
        hourPath.lineTo(0f - hourLength,  - marginHour)
        hourPath.lineTo(0f + hourLength, -marginHour)
        canvas.drawPath(hourPath, hourPaint)
        canvas.restore()
        //画分针
        val marginMinute = radius - longScaleLength - minuteMargin - arrowLength
        val minuteRectF = RectF(- minuteLength, - marginMinute, minuteLength, 0f)
        canvas.save()
        canvas.rotate(minuteRate)
        canvas.drawRoundRect(minuteRectF, 2f, 2f, minutePaint)
        val minutePath = Path()
        minutePath.moveTo(0f, - (marginMinute + arrowLength))
        minutePath.lineTo(0f - minuteLength,  - marginMinute)
        minutePath.lineTo(0f + minuteLength, -marginMinute)
        canvas.drawPath(minutePath, minutePaint)
        canvas.restore()
        //画秒针
        canvas.save()
        val marginSecond = radius - shortScaleLength - secondMargin - arrowLength
        val secondRectF = RectF(- secondLength, - marginSecond, secondLength, 0f)
        canvas.rotate(secondRate)
        canvas.drawRoundRect(secondRectF, 2f, 2f, secondPaint)
        val secondPath = Path()
        secondPath.moveTo(0f, - (marginSecond + arrowLength))
        secondPath.lineTo(0f - secondLength,  - marginSecond)
        secondPath.lineTo(0f + secondLength, -marginSecond)
        canvas.drawPath(secondPath, secondPaint)
        canvas.restore()
    }
}