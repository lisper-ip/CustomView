package app.lonzh.customview

import android.os.Bundle
import app.lonzh.customview.base.BaseActivity
import app.lonzh.customview.databinding.ActivityMainBinding
import app.lonzh.customview.days.ComposeViewActivity
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.wuyr.activitymessenger.startActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        binding.recycleView.linear().divider(R.drawable.driver_black_line).setup {
            addType<ViewBean>(R.layout.item)

            onClick(R.id.tv_item){
                when(getModel<ViewBean>().seq){
                    0 -> {
                        startActivity<ComposeViewActivity>()
                    }
                }
            }
        }.models = getModels()
    }

    private fun getModels() : MutableList<ViewBean>{
        return mutableListOf<ViewBean>().apply {
            add(ViewBean(0, "自定义View练习"))
            add(ViewBean(2, "未完待续"))
        }
    }
}