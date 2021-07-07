package app.lonzh.customview.compose

import android.content.Context
import android.text.InputType
import android.text.Selection
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import app.lonzh.customview.R
import com.blankj.utilcode.util.ClickUtils
import com.google.android.material.textfield.TextInputEditText

/**
 *
 * @ProjectName:    EditPasswordView
 * @Description:    带有显示隐藏密码的编辑框(用addView形式性能更好)
 * @Author:         Lisper
 * @CreateDate:     2021/7/6 2:51 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/6 2:51 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EditPasswordView : LinearLayout {
    private lateinit var textInputEditText: TextInputEditText
    private var iv: ImageView? = null

    constructor(context: Context) : super(context){
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

    private fun initialize(context: Context, attributeSet: AttributeSet?, defStyleInt: Int){
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        textInputEditText = getEditText()
        addView(textInputEditText, 0)


        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.EditPasswordView)
            iv = ImageView(context).apply {
                val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                params.width = typedArray.getDimension(R.styleable.EditPasswordView_img_password_width,
                    resources.getDimension(R.dimen.dp_30)).toInt()
                params.width = typedArray.getDimension(R.styleable.EditPasswordView_img_password_height,
                    resources.getDimension(R.dimen.dp_30)).toInt()
                layoutParams = params
                setImageResource(typedArray.getResourceId(R.styleable.EditPasswordView_img_password_icon, 0))
            }
            addView(iv, 1)

            textInputEditText.run {
                gravity = Gravity.CENTER_VERTICAL
                background = null
                setSingleLine()
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                ellipsize = TextUtils.TruncateAt.END
                transformationMethod =
                    PasswordTransformationMethod.getInstance()
                setPadding(typedArray.getDimension(R.styleable.EditPasswordView_edt_password_padding_start, resources.getDimension(R.dimen.dp_10)).toInt(), 0,
                    typedArray.getDimension(R.styleable.EditPasswordView_edt_password_padding_end, resources.getDimension(R.dimen.dp_0)).toInt(), 0)
                setTextColor(typedArray.getColor(R.styleable.EditPasswordView_edt_password_text_color, 0))
                setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    typedArray.getDimension(R.styleable.EditPasswordView_edt_password_text_size,
                        resources.getDimension(R.dimen.text_14sp)))
                hint = typedArray.getString(R.styleable.EditPasswordView_edt_password_hint)
                setHintTextColor(typedArray.getColor(R.styleable.EditPasswordView_edt_password_hint_color, 0))
                layoutParams.height = typedArray.getDimension(R.styleable.EditPasswordView_edt_password_height,  resources.getDimension(R.dimen.dp_40)).toInt()
            }
            typedArray.recycle()
        }

        ClickUtils.applySingleDebouncing(iv){
            val method = textInputEditText.transformationMethod
            if (method == HideReturnsTransformationMethod.getInstance()) {
                textInputEditText.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                textInputEditText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            it.isSelected = !it.isSelected
            textInputEditText.text?.length?.let { length ->
                Selection.setSelection(textInputEditText.text, length)
            }
        }
    }

    private fun getEditText(): TextInputEditText {
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