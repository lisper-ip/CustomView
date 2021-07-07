package app.lonzh.customview.compose

import android.content.Context
import android.text.Selection
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import app.lonzh.customview.R
import app.lonzh.customview.databinding.LayoutEditPasswordBinding
import com.blankj.utilcode.util.ClickUtils

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
class EditPasswordView : ConstraintLayout {

    private lateinit var binding: LayoutEditPasswordBinding

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
        val view = LayoutInflater.from(context).inflate(R.layout.layout_edit_password, this)
        view.tag = "layout/layout_edit_password_0"
        binding = DataBindingUtil.bind(view)!!

        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.EditPasswordView)
            binding.iv.run {
                setImageResource(typedArray.getResourceId(R.styleable.EditPasswordView_img_password_icon, 0))
            }
            binding.edt.run {
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

        ClickUtils.applySingleDebouncing(binding.iv){
            val method = binding.edt.transformationMethod
            if (method == HideReturnsTransformationMethod.getInstance()) {
                binding.edt.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                binding.edt.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            it.isSelected = !it.isSelected
            binding.edt.text?.length?.let { length ->
                Selection.setSelection(binding.edt.text, length)
            }
        }
    }

    fun getText() : String{
        return binding.edt.text.toString()
    }
}