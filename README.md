# 使用步骤

### 一. 使用系统原生API

1. 声明ViewModel

```
private val mLocationViewModel : LocationViewModel by lazy { ViewModelProvider(this).get(LocationViewModel::class.java) }
```

2. 请求位置更新

```
mLocationViewModel.requestLocationUpdates()
```

3. 读取结果

```
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
```

### 一. 使用谷歌服务API

```
//使用谷歌服务
private val mGmsLocationViewModel : GmsLocationViewModel by lazy { ViewModelProvider(this).get(GmsLocationViewModel::class.java) }
```

2. 请求位置更新

```
mGmsLocationViewModel.requestLocationUpdates()
```

3. 读取结果

```
mGmsLocationViewModel.mLocationState.observe(this) {
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
```