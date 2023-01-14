package priv.wangyuan.location.liblocation.viewmodel

sealed class LiveDataModel<T>

open class LiveDataLastLocationModel<T>(val data: T) : LiveDataModel<T>()
open class LiveDataLocationModel<T>(val data: T) : LiveDataModel<T>()
