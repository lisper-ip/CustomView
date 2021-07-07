package app.lonzh.customview.compose

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import app.lonzh.customview.R
import app.lonzh.customview.databinding.LayoutEditClearBinding
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.StringUtils

/**
 *
 * @ProjectName:    CustomView
 * @Description:    带有清除按键的输入框(用addView形式性能更好)
 * @Author:         Lisper
 * @CreateDate:     2021/7/6 2:51 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/6 2:51 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EditClearView : ConstraintLayout {

    private lateinit var binding: LayoutEditClearBinding

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
        val view = LayoutInflater.from(context).inflate(R.layout.layout_edit_clear, this)
        view.tag = "layout/layout_edit_clear_0"
        binding = DataBindingUtil.bind(view)!!

        attributeSet?.let {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.EditClearView)
            binding.iv.run {
                setImageResource(typedArray.getResourceId(R.styleable.EditClearView_img_clear_icon, 0))
                visibility = GONE
            }
            binding.edt.run {
                setPadding(typedArray.getDimension(R.styleable.EditClearView_edt_clear_padding_start, resources.getDimension(R.dimen.dp_10)).toInt(), 0,
                    typedArray.getDimension(R.styleable.EditClearView_edt_clear_padding_end, resources.getDimension(R.dimen.dp_0)).toInt(), 0)

                setTextColor(typedArray.getColor(R.styleable.EditClearView_edt_clear_text_color, 0))
                setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    typedArray.getDimension(R.styleable.EditClearView_edt_clear_text_size,
                        resources.getDimension(R.dimen.text_14sp)))
                hint = typedArray.getString(R.styleable.EditClearView_edt_clear_hint)
                setHintTextColor(typedArray.getColor(R.styleable.EditClearView_edt_clear_hint_color, 0))
                layoutParams.height = typedArray.getDimension(R.styleable.EditClearView_edt_clear_height,  resources.getDimension(R.dimen.dp_40)).toInt()
            }
            typedArray.recycle()
        }

        ClickUtils.applySingleDebouncing(binding.iv){
            binding.edt.setText("")
        }

        binding.edt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(StringUtils.isEmpty(binding.edt.text)){
                    binding.iv.visibility = View.GONE
                } else {
                    binding.iv.visibility = View.VISIBLE
                }
            }
        })
    }

    fun getText() : String{
        return binding.edt.text.toString()
    }
}