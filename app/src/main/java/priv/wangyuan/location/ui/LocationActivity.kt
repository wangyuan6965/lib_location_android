package priv.wangyuan.location.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_download.*
import priv.wangyuan.download.libdownloader.Downloader
import priv.wangyuan.download.libdownloader.callback.DownloadListener
import priv.wangyuan.download.libdownloader.model.DownloadTask
import java.io.File

class LocationActivity : AppCompatActivity() {
    companion object {
        const val TAG = "DownloadActivity"
        const val url = "https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2020.3.1.23/android-studio-2020.3.1.23-mac_arm.zip"
    }

    private val mListener = object : DownloadListener {
        override fun onStart(task: DownloadTask?) {
            super.onStart(task)
            Log.d(TAG, "onStart,task=${task}")
        }

        override fun onWait(task: DownloadTask?) {
            super.onWait(task)
            Log.d(TAG, "onWait,task=${task}")
        }

        override fun onPause(task: DownloadTask?) {
            super.onPause(task)
            Log.d(TAG, "onPause,task=${task}")
        }

        override fun onDownloading(task: DownloadTask?) {
            super.onDownloading(task)
            Log.d(TAG, "onDownloading,task=${task}")
        }

        override fun onError(task: DownloadTask?) {
            super.onError(task)
            Log.d(TAG, "onError,task=${task}")
        }

        override fun onRemove(task: DownloadTask?) {
            super.onRemove(task)
            Log.d(TAG, "onRemove,task=${task}")
        }

        override fun onFinish(task: DownloadTask?, f: File?) {
            super.onFinish(task, f)
            Log.d(TAG, "onFinish,task=${task}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        Downloader.getDownloader().register(mListener)
        startDownload.setOnClickListener {
            val task = DownloadTask(id = "1160073", version = "1651843318", url)
            Downloader.getDownloader().start(task)
        }
        pauseDownload.setOnClickListener {
            Downloader.getDownloader().pause("1160073")
        }

        deleteDownload.setOnClickListener {
            Downloader.getDownloader().remove("1160073")
        }

        queryDownload.setOnClickListener {
            val task = Downloader.getDownloader().getTask("1160073")
            Log.d(TAG, "task=${task}")
        }
    }

    private fun showToast(msg : String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Downloader.getDownloader().unregister(mListener)
    }
}