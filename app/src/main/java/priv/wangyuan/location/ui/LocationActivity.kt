package priv.wangyuan.location.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import priv.wangyuan.location.liblocation.viewmodel.LiveDataLastLocationModel
import priv.wangyuan.location.liblocation.viewmodel.LiveDataLocationModel
import priv.wangyuan.location.ui.databinding.ActivityLocationBinding
import priv.wangyuan.location.liblocation.viewmodel.LocationViewModel

class LocationActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityLocationBinding

    private lateinit var mLocationViewModel : LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLocationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.btnGetGmsLocation.setOnClickListener {
            mLocationViewModel.requestGmsLocationUpdates()
        }
        mBinding.btnGetLocation.setOnClickListener {
            mLocationViewModel.requestLocationUpdates()
        }
        mLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        mLocationViewModel.mLocationState.observe(this) {
            when(it) {
                is LiveDataLastLocationModel -> {
                    val txt = "上次获取到的位置${it.data?.toString()}"
                    mBinding.tvLastLocation.text = txt
                }
                is LiveDataLocationModel -> {
                    val txt = "当前位置=${it.data?.toString()}"
                    mBinding.tvLocation.text = txt
                }
            }
        }
    }
}