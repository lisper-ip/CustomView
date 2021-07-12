package app.lonzh.customview.days

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.activity.viewModels
import app.lonzh.customview.R
import app.lonzh.customview.base.BaseActivity
import app.lonzh.customview.databinding.ActivityComposeViewBinding
import app.lonzh.customview.vm.ComposeViewViewModel
import java.util.*

/**
 *
 * @ProjectName:    CustomView
 * @Description:    组合自定义View练习
 * @Author:         Lisper
 * @CreateDate:     2021/7/6 2:42 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/6 2:42 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ComposeViewActivity : BaseActivity<ActivityComposeViewBinding>() {

    private val timer = Timer()

    private val timeTask = object : TimerTask(){
        override fun run() {
            composeViewViewModel.circleProgress.set(count % binding.circleProgress.getMaxProgress())
            count ++
        }
    }
    private var count: Int = 0

    private val composeViewViewModel: ComposeViewViewModel by viewModels()

    override val layoutId: Int = R.layout.activity_compose_view

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = composeViewViewModel
        timer.schedule(timeTask, 0, 300)
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
}