package priv.wangyuan.location.liblocation

import android.location.Geocoder
import android.location.Location
import com.blankj.utilcode.util.Utils
import priv.wangyuan.location.liblocation.model.GpsAddress
import java.util.*

/**
 * Address[addressLines=[0:"北京市朝阳区春华路辅路"],
 * feature=null,admin=北京市,sub-admin=来广营(地区)乡,
 * locality=北京市,thoroughfare=春华路辅路,postalCode=null,
 * countryCode=CN,countryName=中国,
 * hasLatitude=true,latitude=40.04726833306925,hasLongitude=true,longitude=116.43413632860037,
 * phone=null,url=null,extras=null]
 */
object GeocoderApi {

    private val mGeocoder by lazy { Geocoder(Utils.getApp(), Locale.getDefault()) }

    fun getFromLocation(location: Location): GpsAddress? {
        val addresses = mGeocoder.getFromLocation(location.latitude, location.longitude, 5)
        if (addresses.isEmpty()) return null
        val address = addresses[0]
        return GpsAddress(address)
    }
}