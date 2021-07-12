package app.lonzh.customview.base

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @ProjectName:    CustomView
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/6 2:33 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/6 2:33 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseActivity<DB: ViewDataBinding> : AppCompatActivity(), Handler.Callback{
    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initView(savedInstanceState)
    }

    abstract val layoutId: Int

    abstract fun initView(savedInstanceState: Bundle?)

    override fun handleMessage(msg: Message): Boolean {
        return true
    }
}