package app.lonzh.customview.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import app.lonzh.customview.R
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @ProjectName:    CustomView
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/9 4:36 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/9 4:36 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class RatingBar : View {
    companion object {
        //五角星
        const val NUMBER = 5
    }

    //星星的最小高度(不包含margin)
    private var minViewHeight: Int = 0

    //星星的数量
    private var starNumber: Int = 0

    //原始颜色
    private var srcColor: Int = 0

    //进度颜色
    private var desColor: Int = 0

    //星星之间的距离
    private var starMargin: Float = 0f

    //外圆半径
    private var outCircleRadius: Float = 0f

    //内圆半径
    private var inCircleRadius: Float = 0f

    private lateinit var srcPaint: Paint

    private lateinit var desPaint: Paint

    //最大进度
    private var ratingBarMaxProgress: Int = 0

    //当前进度
    private var ratingBarProgress: Int = 0

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar)
        minViewHeight = resources.getDimension(R.dimen.dp_20).toInt()

        srcColor = typedArray.getColor(
            R.styleable.RatingBar_ratingBar_src_color,
            ContextCompat.getColor(context, R.color.black50)
        )
        desColor = typedArray.getColor(
            R.styleable.RatingBar_ratingBar_des_color,
            ContextCompat.getColor(context, R.color.black)
        )
        starNumber = typedArray.getInt(
            R.styleable.RatingBar_ratingBar_star_number,
            5
        )

        starMargin = resources.getDimension(R.dimen.dp_4)

        ratingBarMaxProgress = typedArray.getInt(
            R.styleable.RatingBar_ratingBar_max_progress,
            5
        )

        ratingBarProgress = typedArray.getInt(
            R.styleable.RatingBar_ratingBar_progress,
            0
        )

        srcPaint = Paint().apply {
            color = srcColor
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
            style = Paint.Style.FILL
        }

        desPaint = Paint().apply {
            color = desColor
            isAntiAlias = true
            isDither = true
            isSubpixelText = true
            style = Paint.Style.FILL
        }

        typedArray.recycle()
    }

    private fun getStarPath(): Path {
        val path = Path()
        //五角星 平均角度
        val angleRate = 360 / NUMBER
        //右上角的大圆的第一个角
        val outAngle = 90 - angleRate
        //小圆的第一个角
        val inAngle = angleRate / 2 + outAngle

        for (index in 0..NUMBER) {
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
        return path
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        outCircleRadius = h * 0.95f / 2
        inCircleRadius = outCircleRadius / 2
    }

    //角度转弧度
    private fun angelToArc(angle: Float): Double {
        return angle * Math.PI / 180f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()

        val coverSize = (height * starNumber + starMargin * (starNumber - 1)) * ratingBarProgress * 1.0f / ratingBarMaxProgress
        val srcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val srcCanvas = Canvas(srcBitmap)
        drawStar(srcCanvas, srcPaint)

        val desBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val desCanvas = Canvas(desBitmap)
        desCanvas.save()
        //先裁剪 在画
        desCanvas.clipRect(0f, 0f, coverSize, height.toFloat())
        drawStar(desCanvas, desPaint)
        desCanvas.restore()
        desCanvas.drawBitmap(srcBitmap, 0f, 0f, srcPaint)

        canvas.drawBitmap(desBitmap, 0f, 0f, null)
        canvas.restore()
    }

    private fun drawStar(canvas: Canvas, paint: Paint) {
        for (index in 0 until starNumber) {
            canvas.save()
            canvas.translate(
                (height / 2).toFloat() + index * (height + starMargin),
                (height / 2).toFloat()
            )
            val path = getStarPath()
            canvas.drawPath(path, paint)
            canvas.restore()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(
                (minViewHeight * starNumber + starMargin * (starNumber - 1)).toInt(),
                minViewHeight
            )
        } else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension((heightSize * starNumber + starMargin * (starNumber - 1)).toInt(),
                heightSize)
        } else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, minViewHeight)
        }
    }
}