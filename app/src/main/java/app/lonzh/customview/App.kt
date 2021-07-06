package app.lonzh.customview

import android.app.Application
import com.drake.brv.utils.BRV

/**
 *
 * @ProjectName:    CustomView
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/6 2:04 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/6 2:04 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        BRV.modelId = BR.bean
    }
}