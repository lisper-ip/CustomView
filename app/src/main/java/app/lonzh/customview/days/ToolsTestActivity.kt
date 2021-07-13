package app.lonzh.customview.days

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.activity.viewModels
import app.lonzh.customview.R
import app.lonzh.customview.base.BaseActivity
import app.lonzh.customview.databinding.ActivityToolsTestBinding
import app.lonzh.customview.vm.ToolsViewModel

/**
 *
 * @ProjectName:    CustomView
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/13 10:37 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/13 10:37 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ToolsTestActivity : BaseActivity<ActivityToolsTestBinding>() {

    companion object{
        const val TAG = "ToolsTestActivity"
    }

    private val toolsViewModel: ToolsViewModel by viewModels()

    private lateinit var configuration: Configuration

    private lateinit var viewConfiguration: ViewConfiguration

    private lateinit var stringBuilder: StringBuilder

    //手势第一步 实现监听器
    private val gestureDetectorImp = object : GestureDetector.OnGestureListener{
        //触摸屏幕触发
        override fun onDown(p0: MotionEvent?): Boolean {
            Log.e(TAG, "onDown")
            return false
        }

        //手指按下 没松开和移动时调用
        override fun onShowPress(p0: MotionEvent?) {
            Log.e(TAG, "onShowPress")
        }

        //点击屏幕调用
        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            Log.e(TAG, "onSingleTapUp")
            return false
        }

        //手指滑动(多次调用)
        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            Log.e(TAG, "onScroll")
            return false
        }

        //长按时触发
        override fun onLongPress(p0: MotionEvent?) {
            Log.e(TAG, "onLongPress")
        }

        //手指在屏幕拖动时触发(应该触发一次)
        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            Log.e(TAG, "onFling")
            return false
        }

    }

    private lateinit var gestureDetector: GestureDetector

    override val layoutId: Int = R.layout.activity_tools_test

    @SuppressLint("ClickableViewAccessibility")
    override fun initView(savedInstanceState: Bundle?) {
        stringBuilder = StringBuilder()
        binding.vm = toolsViewModel

        configuration = resources.configuration
        stringBuilder.append("Configuration\n")
        stringBuilder.append("移动国家码 ${configuration.mcc}\n")
        stringBuilder.append("移动网络码 ${configuration.mnc}\n")
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            stringBuilder.append("屏幕方向: 竖屏\n")
        } else {
            stringBuilder.append("屏幕方向: 横屏\n")
        }
        stringBuilder.append("\n")

        viewConfiguration = ViewConfiguration.get(this)
        stringBuilder.append("ViewConfiguration\n")
        stringBuilder.append("屏幕最小的滑动距离: ${viewConfiguration.scaledTouchSlop}\n")
        stringBuilder.append("fling最大值: ${viewConfiguration.scaledMaximumFlingVelocity}\n")
        stringBuilder.append("fling最小值: ${viewConfiguration.scaledMinimumFlingVelocity}\n")
        stringBuilder.append("是否有物理按键: ${viewConfiguration.hasPermanentMenuKey()}\n")
        stringBuilder.append("双击间隔时间(时间内是双击 否则单击): ${ViewConfiguration.getDoubleTapTimeout()}\n")
        stringBuilder.append("按住状态转为长按状态需要的时间: ${ViewConfiguration.getLongPressTimeout()}\n")
        stringBuilder.append("重复按键的时间: ${ViewConfiguration.getKeyRepeatTimeout()}\n")
        stringBuilder.append("\n")




        toolsViewModel.text.set(stringBuilder.toString())

        //手势第二步 创建手势
        gestureDetector = GestureDetector(this, gestureDetectorImp)
//        binding.tv.setOnTouchListener { _, event ->
//            gestureDetector.onTouchEvent(event)
//        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //第三步 接管事件
        return gestureDetector.onTouchEvent(event)
    }
}