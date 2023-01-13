package priv.wangyuan.location.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Looper
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import kotlinx.android.synthetic.main.activity_index.*

class IndexPage : AppCompatActivity(), View.OnClickListener {
    private var current = TOTAL_COUNT_DOWN
    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == MSG_FIRST) {
                current--
                val txt = "${current}s"
                tvCountDownTime.text = txt
                if (current <= 0) {
                    openLoginPage()
                } else {
                    sendEmptyMessageDelayed(MSG_FIRST, 1000)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        setContentView(R.layout.activity_index)

        /*
         * 设置沉浸模式
         */
        SystemBarHelper.setAdaptStatusBar(window)
        /*
         * 设置状态栏文字为白色
         */
        SystemBarHelper.setStatusBarDarkMode(window, false)
        tvCountDownTime.setText(R.string.three_seconds)
        tvCountDownTime.setOnClickListener(this)
        mHandler.sendEmptyMessageDelayed(MSG_FIRST, 1000)
    }

    override fun onClick(v: View) {
        if (v == tvCountDownTime) {
            openLoginPage()
        }
    }

    private fun openLoginPage() {
        mHandler.removeMessages(MSG_FIRST)
        startActivity(Intent(this, LocationActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeMessages(MSG_FIRST)
    }

    companion object {
        private const val TOTAL_COUNT_DOWN = 1
        private const val MSG_FIRST = 1001
    }
}