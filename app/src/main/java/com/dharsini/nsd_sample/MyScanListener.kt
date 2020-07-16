package com.dharsini.nsd_sample

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

interface NetworkDiscovery {
    fun discoveryStatus(msg: String)
    fun onServiceFound(serviceInfo: NsdServiceInfo?)
}

class MyScanListener(val mNetworkDiscovery:NetworkDiscovery) : NsdManager.DiscoveryListener {
    override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
        mNetworkDiscovery.onServiceFound(serviceInfo)
    }

    override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
        mNetworkDiscovery.discoveryStatus("Stop discovery failed")
    }

    override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
        mNetworkDiscovery.discoveryStatus("Start discovery failed")
    }

    override fun onDiscoveryStarted(serviceType: String?) {
        mNetworkDiscovery.discoveryStatus("Discovery started")
    }

    override fun onDiscoveryStopped(serviceType: String?) {
        mNetworkDiscovery.discoveryStatus("Discovery stopped")
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo?) {
        mNetworkDiscovery.discoveryStatus("Service Lost")
    }
}