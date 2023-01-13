package priv.wangyuan.location.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter

class BaseApplication : Application() {

    companion object {
        const val APP_ID = "location"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        initARouter()
        //前后台切换检测
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onAppForeground() {
                Log.e("BaseApplication", "onAppForeground")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onAppBackground() {
                Log.e("BaseApplication", "onAppBackground")
            }
        })
    }

    private fun initARouter() {
        // 打印日志,默认关闭
        ARouter.openLog()
        // 开启调试模式，默认关闭(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.openDebug()
        // 打印日志的时候打印线程堆栈
        ARouter.printStackTrace()
        ARouter.init(this)
    }
}