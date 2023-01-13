package priv.wangyuan.location.ui

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import priv.wangyuan.location.liblocation.GeocoderApi
import priv.wangyuan.location.liblocation.LocationApi
import priv.wangyuan.location.liblocation.listener.GpsLocationListener
import priv.wangyuan.location.ui.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityLocationBinding

    private val mGpsLocationListener = object : GpsLocationListener {
        override fun onLastLocationChanged(location: Location?) {
            super.onLastLocationChanged(location)
            location?.also {
                val txt = "上次获取到的位置${GeocoderApi.getFromLocation(it)?.toString()}"
                mBinding.tvLastLocation.text = txt
            }
        }

        override fun onLocationChanged(location: Location?) {
            super.onLocationChanged(location)
            location?.also {
                val txt = "当前位置=${GeocoderApi.getFromLocation(it)?.toString()}"
                mBinding.tvLocation.text = txt
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLocationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.btnGetLocation.setOnClickListener {
            LocationApi.getInstance().requestLocationUpdates(mGpsLocationListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationApi.getInstance().removeLocationUpdates(mGpsLocationListener)
    }
}