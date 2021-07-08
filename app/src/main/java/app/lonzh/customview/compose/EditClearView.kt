package app.lonzh.customview.compose

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import app.lonzh.customview.R
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.StringUtils
import com.google.android.material.textfield.TextInputEditText

/**
 *
 * @ProjectName:    CustomView
 * @Description:    带有清除按键的输入框(addView形式)
 * @Author:         Lisper
 * @CreateDate:     2021/7/6 2:51 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/6 2:51 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EditClearView : LinearLayout {
    private lateinit var textInputEditText: TextInputEditText
    private var iv: ImageView? = null

    constructor(context: Context) : super(context, null){
        initialize(context, null, 0)
    }

    //xml布局中使用自定义View属性时调用
    constructor(
        context: Context, attributeSet: AttributeSet
    ) : super(context, attributeSet, 0){
        initialize(context, attributeSet, 0)
    }

    //以下两个方法与主题有关
    constructor(
        context: Context, attributeSet: AttributeSet, defStyleInt: Int
    ) : super(context, attributeSet, defStyleInt, 0){
        initialize(context, attributeSet, defStyleInt)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleInt: Int){
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        textInputEditText = getEditText()
        addView(textInputEditText, 0)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditClearView)
            if(typedArray.hasValue(R.styleable.EditClearView_img_clear_icon)){
                iv = ImageView(context).apply {
                    val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    params.width = typedArray.getDimension(R.styleable.EditClearView_img_clear_width,
                        resources.getDimension(R.dimen.dp_30)).toInt()
                    params.width = typedArray.getDimension(R.styleable.EditClearView_img_clear_height,
                        resources.getDimension(R.dimen.dp_30)).toInt()
                    layoutParams = params
                    setImageResource(typedArray.getResourceId(R.styleable.EditClearView_img_clear_icon, 0))
                    visibility = GONE
                }
                ClickUtils.applySingleDebouncing(iv){
                    textInputEditText.setText("")
                }
                addView(iv, 1)
            }
            textInputEditText.run {
                gravity = Gravity.CENTER_VERTICAL
                background = null
                setSingleLine()
                ellipsize = TextUtils.TruncateAt.END
                setPadding(typedArray.getDimension(R.styleable.EditClearView_edt_clear_padding_start, resources.getDimension(R.dimen.dp_10)).toInt(), 0,
                    typedArray.getDimension(R.styleable.EditClearView_edt_clear_padding_end, resources.getDimension(R.dimen.dp_0)).toInt(), 0)
                setTextColor(typedArray.getColor(R.styleable.EditClearView_edt_clear_text_color, 0))
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    typedArray.getDimension(R.styleable.EditClearView_edt_clear_text_size,
                        resources.getDimension(R.dimen.text_14sp)))
                hint = typedArray.getString(R.styleable.EditClearView_edt_clear_hint)
                setHintTextColor(typedArray.getColor(R.styleable.EditClearView_edt_clear_hint_color, 0))
                layoutParams.height = typedArray.getDimension(R.styleable.EditClearView_edt_clear_height,  resources.getDimension(R.dimen.dp_40)).toInt()

                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        iv?.let {
                            if(StringUtils.isEmpty(textInputEditText.text)){
                                it.visibility = View.GONE
                            } else {
                                it.visibility = View.VISIBLE
                            }
                        }
                    }
                })
            }
            typedArray.recycle()
        }
    }

    private fun getEditText(): TextInputEditText{
        val textInputEditText = TextInputEditText(context)
        val layoutParams = LayoutParams(1, LayoutParams.WRAP_CONTENT)
        layoutParams.weight = 1f
        textInputEditText.layoutParams = layoutParams
        return textInputEditText
    }

    fun getText() : String{
        return textInputEditText.text.toString()
    }
}