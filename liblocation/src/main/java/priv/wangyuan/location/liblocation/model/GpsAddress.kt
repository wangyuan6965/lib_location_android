package priv.wangyuan.location.liblocation.model

import android.location.Address

/**
 * Address[addressLines=[0:"北京市朝阳区春华路辅路"],
 * feature=null,admin=北京市,sub-admin=来广营(地区)乡,
 * locality=北京市,thoroughfare=春华路辅路,postalCode=null,
 * countryCode=CN,countryName=中国,
 * hasLatitude=true,latitude=40.04726833306925,hasLongitude=true,longitude=116.43413632860037,
 * phone=null,url=null,extras=null]
 */
class GpsAddress(address: Address) {
    private val latitude: Double?//北纬
    private val longitude: Double?//东经
    private val countryCode: String? //国家编码
    private val countryName: String? //国家名
    private val locality: String? //省级行政区名称
    private val subLocality: String? //市级行政区名称
    private val adminArea: String? //区/县级行政区名称
    private val subAdminArea: String? //乡镇/街道级行政区名称
    private val thoroughfare: String? //道路名称
    private val subThoroughfare: String? //子道路名称

    init {
        latitude = address.latitude
        longitude = address.longitude
        countryCode = address.countryCode
        countryName = address.countryName
        adminArea = address.adminArea
        subAdminArea = address.subAdminArea
        locality = address.locality
        subLocality = address.subLocality
        thoroughfare = address.thoroughfare
        subThoroughfare = address.subThoroughfare
    }

    override fun toString(): String {
        return "GpsAddress(latitude=$latitude, longitude=$longitude, countryCode=$countryCode, countryName=$countryName, adminArea=$adminArea, subAdminArea=$subAdminArea, locality=$locality, subLocality=$subLocality, thoroughfare=$thoroughfare, subThoroughfare=$subThoroughfare)"
    }


}