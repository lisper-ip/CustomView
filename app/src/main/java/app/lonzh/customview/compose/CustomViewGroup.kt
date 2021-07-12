package app.lonzh.customview.compose

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 *
 * @ProjectName:    CustomView
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/12 12:04 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/12 12:04 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CustomViewGroup : ViewGroup {
    constructor(context: Context) : super(context, null) {
        initialize(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        initialize(context, attrs, 0)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleInt: Int) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }
}