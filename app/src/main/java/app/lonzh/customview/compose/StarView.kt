package app.lonzh.customview.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import app.lonzh.customview.R
import com.blankj.utilcode.util.ScreenUtils
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @ProjectName:    CustomView
 * @Description:    五角星
 * @Author:         Lisper
 * @CreateDate:     2021/7/9 11:31 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/9 11:31 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class StarView : View {

    companion object {
        const val FILL = 0
        const val STROKE = 1

        //默认五角星
        const val ANGLE_NUMBER = 5

        //填充百分比
        const val FILL_PERCENT = 1.0f
    }

    private var screenWidth: Int = 0

    //星星的最小高度(不包含margin)
    private var minViewHeight: Int = 0

    //填充百分比
    private var fillPercent: Float = 0f

    //线条宽度
    private var lineWidth: Float = 0f

    //选中星星的颜色
    private var starSelectColor: Int = 0

    //星星线条的颜色
    private var starLineColor: Int = 0

    //几个角
    private var number: Int = 0

    //外圆半径
    private var outCircleRadius: Float = 0f

    //内圆半径
    private var inCircleRadius: Float = 0f

    private lateinit var paint: Paint

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

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StarView)

        starLineColor = typedArray.getColor(
            R.styleable.StarView_star_line_color,
            ContextCompat.getColor(context, R.color.black)
        )
        starSelectColor = typedArray.getColor(
            R.styleable.StarView_star_select_color,
            ContextCompat.getColor(context, R.color.black)
        )

        lineWidth = typedArray.getDimension(
            R.styleable.StarView_star_stroke_width,
            resources.getDimension(R.dimen.dp_1)
        )

        fillPercent = typedArray.getFloat(
            R.styleable.StarView_star_fill_percent,
            FILL_PERCENT
        )

        number = typedArray.getInt(R.styleable.StarView_star_number, ANGLE_NUMBER)

        val flag = typedArray.getInt(R.styleable.StarView_star_flag, STROKE)

        paint = Paint().apply {
            isDither = true
            isAntiAlias = true
            isSubpixelText = true
            color = starLineColor
            if (flag == FILL) {
                style = Paint.Style.FILL
            } else {
                style = Paint.Style.STROKE
                strokeWidth = lineWidth
            }
        }
        minViewHeight = resources.getDimension(R.dimen.dp_50).toInt()

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(minViewHeight, minViewHeight)
        } else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(minViewHeight, heightSize)
        } else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, minViewHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        outCircleRadius = h * 0.95f / 2
        inCircleRadius = outCircleRadius / 2
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        val path = Path()
        //平均角度
        val angleRate = 360 / number
        //右上角的大圆的第一个角
        val outAngle = 90 - angleRate
        //小圆的第一个角
        val inAngle = angleRate / 2 + outAngle

        for (index in 0..number) {
            val outCirclePointX =
                cos(angelToArc((outAngle + index * angleRate).toFloat())) * outCircleRadius
            val outCirclePointY =
                -sin(angelToArc((outAngle + index * angleRate).toFloat())) * outCircleRadius

            val inCirclePointX =
                cos(angelToArc((inAngle + index * angleRate).toFloat())) * inCircleRadius
            val inCirclePointY =
                -sin(angelToArc((inAngle + index * angleRate).toFloat())) * inCircleRadius

            if (index == 0) {
                path.moveTo(outCirclePointX.toFloat(), outCirclePointY.toFloat())
            } else {
                path.lineTo(outCirclePointX.toFloat(), outCirclePointY.toFloat())
            }
            path.lineTo(inCirclePointX.toFloat(), inCirclePointY.toFloat())
        }
        path.close()
        canvas.drawPath(path, paint)
        canvas.restore()
    }

    //角度转弧度
    private fun angelToArc(angle: Float): Double {
        return Math.toRadians(angle.toDouble())
    }
}