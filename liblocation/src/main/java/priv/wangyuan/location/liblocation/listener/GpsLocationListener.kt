package priv.wangyuan.location.liblocation.listener

import android.location.Location

interface GpsLocationListener {

    fun onLastLocationChanged(location: Location?) {

    }

    fun onLocationProviderDisabled() {

    }

    fun onLocationChanged(location: Location) {

    }

    fun onLocationPermissionDenied() {

    }

}