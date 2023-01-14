package priv.wangyuan.location.ui.viewmodel

import android.app.Application
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import priv.wangyuan.location.liblocation.GeocoderApi
import priv.wangyuan.location.liblocation.GmsLocationApi
import priv.wangyuan.location.liblocation.LocationApi
import priv.wangyuan.location.liblocation.listener.GpsLocationListener
import priv.wangyuan.location.liblocation.model.GpsAddress
import priv.wangyuan.location.liblocation.viewmodel.LiveDataLastLocationModel
import priv.wangyuan.location.liblocation.viewmodel.LiveDataLocationModel
import priv.wangyuan.location.liblocation.viewmodel.LiveDataModel

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    val mLocationState = MutableLiveData<LiveDataModel<GpsAddress?>>()

    private val mGpsLocationListener = object : GpsLocationListener {
        override fun onLastLocationChanged(location: Location?) {
            super.onLastLocationChanged(location)
            location?.also {
                //上次获取到的位置
                mLocationState.postValue(LiveDataLastLocationModel(GeocoderApi.getFromLocation(it)))
            }
        }

        override fun onLocationChanged(location: Location?) {
            super.onLocationChanged(location)
            location?.also {
                //当前位置
                mLocationState.postValue(LiveDataLocationModel(GeocoderApi.getFromLocation(it)))
            }
        }

        override fun onLocationPermissionDenied() {
            super.onLocationPermissionDenied()
            Toast.makeText(application, "定位权限被拒绝", Toast.LENGTH_LONG).show()
        }

        override fun onLocationProviderDisabled() {
            super.onLocationProviderDisabled()
            Toast.makeText(application, "定位服务不可用", Toast.LENGTH_LONG).show()
        }
    }

    fun requestLocationUpdates() {
        LocationApi.getInstance().requestLocationUpdates(mGpsLocationListener)
    }

    fun requestGmsLocationUpdates() {
        GmsLocationApi.getInstance().requestLocationUpdates(mGpsLocationListener)
    }

    override fun onCleared() {
        super.onCleared()
        LocationApi.getInstance().removeLocationUpdates(mGpsLocationListener)
        GmsLocationApi.getInstance().removeLocationUpdates(mGpsLocationListener)
    }

}