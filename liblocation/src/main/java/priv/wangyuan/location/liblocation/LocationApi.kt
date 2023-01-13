package priv.wangyuan.location.liblocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.Utils
import priv.wangyuan.location.liblocation.listener.GpsLocationListener
import java.util.WeakHashMap

class LocationApi private constructor() {

    private val mListeners = WeakHashMap<GpsLocationListener, String>()

    private val mLocationManager by lazy {
        Utils.getApp().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun checkPermission(): Boolean {
        val context = Utils.getApp()
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getEnabledProvider(): String? {
        return if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationManager.GPS_PROVIDER
        } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LocationManager.NETWORK_PROVIDER
        } else {
            null
        }
    }

    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            mListeners.keys.forEach { l ->
                l.onLocationChanged(location)
            }
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            mListeners.keys.forEach { l ->
                l.onLocationProviderDisabled()
            }
        }
    }

    fun requestLocationUpdates(l : GpsLocationListener) {
        mListeners[l] = ""
        if (!checkPermission()) {
            mListeners.keys.forEach {
                it.onLocationPermissionDenied()
            }
            return
        }
        getEnabledProvider()?.also {
            val location = mLocationManager.getLastKnownLocation(it)
            mListeners.keys.forEach { l ->
                l.onLastLocationChanged(location)
            }
            mLocationManager.requestLocationUpdates(it, 5000, 10f, mLocationListener)
        } ?: run {
            mListeners.keys.forEach {
                it.onLocationProviderDisabled()
            }
        }
    }

    fun removeLocationUpdates(l : GpsLocationListener) {
        mListeners.remove(l)
        mLocationManager.removeUpdates(mLocationListener)
    }

    fun requestGmsLocationUpdates() {
        if (checkPermission()) {
            return
        }
        getEnabledProvider()?.also {
            mLocationManager.requestLocationUpdates(it, 5000, 10f, mLocationListener)
        } ?: run {

        }
    }

    fun removeGmsLocationUpdates() {
        if (checkPermission()) {
            return
        }
        mLocationManager.removeUpdates(mLocationListener)
    }

    private object Holder {
        val INSTANCE = LocationApi()
    }

    companion object {
        @JvmStatic
        fun getInstance() : LocationApi {
            return Holder.INSTANCE
        }
    }
}