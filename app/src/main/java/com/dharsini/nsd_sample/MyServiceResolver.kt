package com.dharsini.nsd_sample

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

interface MyResolvedListener {
    fun onServiceResolved(data: ScannedData)
}

class MyServiceResolver(val mMyResolvedListener: MyResolvedListener) : NsdManager.ResolveListener {
    override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {

    }

    override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
        mMyResolvedListener.onServiceResolved(
            ScannedData(
                serviceType = serviceInfo?.serviceType ?: "",
                serviceName = serviceInfo?.serviceName ?: "",
                port = serviceInfo?.port ?: 0,
                hostAddress = serviceInfo?.host,
                hostAddressValue = serviceInfo?.host?.hostAddress
            )
        )
    }
}