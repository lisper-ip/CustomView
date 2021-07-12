package app.lonzh.customview.compose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import app.lonzh.customview.R
import com.blankj.utilcode.util.ScreenUtils


/**
 *
 * @ProjectName:    CustomView
 * @Description:    练习基础
 * @Author:         Lisper
 * @CreateDate:     2021/7/8 11:47 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/8 11:47 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class TestView : View {
    private var screenWidth : Int = 0
    private var screenHeight : Int = 0

    private lateinit var fillPaint: Paint
    private lateinit var strokePaint: Paint
    private lateinit var textPaint: Paint

    private lateinit var bitmap: Bitmap

    constructor(context: Context) : super(context, null) {
        initialize(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        initialize(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleInt: Int) : super(
        context,
        attrs,
        defStyleInt,
        0
    ){
        initialize(context, attrs, defStyleInt)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleInt: Int){
        screenWidth = ScreenUtils.getScreenWidth()
        screenHeight = ScreenUtils.getScreenHeight()

        bitmap = getBitmap(context, R.mipmap.ic_launcher)
        initPaint(context)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getBitmap(context: Context, resId: Int): Bitmap{
        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable = context.getDrawable(resId)
            bitmap = Bitmap.createBitmap(
                vectorDrawable!!.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)
        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, resId)
        }
        return bitmap
    }

    private fun initPaint(context: Context){
        fillPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.black)
            style = Paint.Style.FILL
            //防抖动 柔和
            isDither = true
            //是否抗锯齿
            isAntiAlias = true
            //虚线  实-虚-实-虚  数组最少2个
            //pathEffect = DashPathEffect(floatArrayOf(5f, 40f, 10f, 20f), 10f)
        }

        strokePaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.black)
            style = Paint.Style.STROKE
            strokeWidth = resources.getDimension(R.dimen.dp_1)
            isAntiAlias = true
            isDither = true
            //LCD
            isSubpixelText = true
        }

        textPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.purple_200)
            textSize = resources.getDimension(R.dimen.text_14sp)
            //textAlign = Paint.Align.RIGHT
            isUnderlineText = true
            isFakeBoldText = true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val width = if(widthMode == MeasureSpec.EXACTLY){
            MeasureSpec.getSize(widthMeasureSpec)
        } else {
            screenWidth
        }
        val height = if(heightMode == MeasureSpec.EXACTLY){
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            screenWidth
        }
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        //移动到中心
        //canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        //画圆
        //canvas.drawCircle(0f, 0f, (width / 3).toFloat(), strokePaint)
        canvas.restore()

        canvas.save()
        //划线
        canvas.drawLine(width.toFloat() / 2, 0f, width.toFloat() / 2, height.toFloat(), fillPaint)
        canvas.drawLine(0f, height.toFloat() / 2, width.toFloat(), height.toFloat() / 2, fillPaint)
        canvas.restore()

        canvas.save()
        //x轴
        //canvas.drawLine(0f,0f, width.toFloat(), 0f, strokePaint)
        //y轴
        //canvas.drawLine(0f,0f, 0f, height.toFloat(), strokePaint)
        canvas.restore()

        canvas.save()
        //canvas.drawRGB(139, 197, 186)
        //第一个参数未透明度
        //canvas.drawARGB(100, 139, 197, 186)
        canvas.restore()

        canvas.save()
//        val text = "韩里凯"
//        val rect = Rect()
//        textPaint.getTextBounds(text, 0, text.length, rect)
//        canvas.translate(0f, (rect.bottom - rect.top).toFloat())
//        //canvas.rotate(20f)
//        canvas.drawText(text, (width / 2).toFloat(), 0f, textPaint)
        canvas.restore()

        canvas.save()
//        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
//        //画点  stroke必须设置 不然画不出点
//        canvas.drawPoint(0f, 0f, strokePaint)
        canvas.restore()

        canvas.save()
        //绘制方形
        //val rect = Rect(width / 4, 0, width / 2, height / 4)
        //canvas.drawRect(rect, fillPaint)
        canvas.restore()

        canvas.save()
//        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
//        val rectF = RectF((- width / 4).toFloat(), (-height / 6).toFloat(),
//            (width / 4).toFloat(), (height / 6).toFloat()
//        )
//        //绘制椭圆
//        canvas.drawOval(rectF, strokePaint)
        canvas.restore()

        canvas.save()
//        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
//        canvas.rotate(-90f)
//        val rectF = RectF(
//            (-width / 4).toFloat(), (-height / 6).toFloat(),
//            (width / 4).toFloat(), (height / 6).toFloat()
//        )
//        //绘制弧面
//        canvas.drawArc(rectF, 0f, 90f, false,  strokePaint)
        canvas.restore()


        canvas.save()
        //完全绘制
        //canvas.drawBitmap(bitmap, 0f, 0f, fillPaint)
//        val srcRect = Rect(0, 0, bitmap.width, (bitmap.height * 0.5).toInt())
//        val desRect = Rect(width / 2, height / 2, width / 2 + bitmap.width,
//            (height/ 2 + bitmap.height * 0.5).toInt()
//        )
//        canvas.drawBitmap(bitmap, srcRect, desRect, fillPaint)
        canvas.restore()


        canvas.save()
        val path = Path()
//        val rectFArc = RectF(0f, 0f, (width/2).toFloat(), (height/ 2).toFloat())
//        //添加圆弧
//        path.addArc(rectFArc, 0f, 90f)
//        val ovalRectF = RectF((width/2).toFloat(), (height/8).toFloat(), width.toFloat(), (height * 3/ 8).toFloat())
//        //添加椭圆
//        path.addOval(ovalRectF, Path.Direction.CCW)
//        //添加circle
//        path.addCircle((width/4).toFloat(), (height * 3 / 4).toFloat(), (width/4).toFloat(), Path.Direction.CW)
//        //添加rect
//        val rectF = RectF((width/2).toFloat(), (height/2).toFloat(), (width * 3 /4).toFloat(), (height  / 2 + 20).toFloat())
//        path.addRect(rectF, Path.Direction.CCW)
//        path.addRoundRect(rectF, 20f, 20f, Path.Direction.CCW)
//        path.moveTo(0f, 0f)
//        path.lineTo((width/2).toFloat(), (height/4).toFloat())
//        path.arcTo(ovalRectF, 180f, 90f)
//        path.moveTo(0f, (height * 3/ 4).toFloat())
//        //二阶贝塞尔曲线  抛物线
//        //path.quadTo((width/4).toFloat(), 0f, (width/2).toFloat(), (height * 3 /4).toFloat())
//        //三阶贝塞尔曲线
//        path.cubicTo((width/4).toFloat(), 0f, (width * 3/4).toFloat(), 0f,
//            width.toFloat(),
//            (height * 3 / 4).toFloat()
//        )
        canvas.drawPath(path, strokePaint)
        canvas.restore()

        canvas.save()

        val bitmapIcon = Bitmap.createBitmap(bitmap.width, bitmap.height,
            Bitmap.Config.ARGB_8888)
        val bitmapCanvas = Canvas(bitmapIcon)
        bitmapCanvas.clipRect(0, 0, bitmap.width /2, bitmap.height)
        bitmapCanvas.drawBitmap(bitmap, 0f, 0f, fillPaint)
        canvas.drawBitmap(bitmapIcon, 0f, 0f, null)

        canvas.restore()
    }
}