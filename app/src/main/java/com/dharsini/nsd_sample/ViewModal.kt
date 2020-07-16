package com.dharsini.nsd_sample


import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import java.net.InetAddress


class ViewModal(val mApplication: Application) : AndroidViewModel(mApplication),
    RegistrationListener, NetworkDiscovery, MyResolvedListener {

    lateinit var mNsdManager: NsdManager
    var registerCall=false
    var discoveryCall=false

    var data: MediatorLiveData<ArrayList<ScannedData>> = MediatorLiveData()


    init {
        mNsdManager = mApplication.getSystemService(Context.NSD_SERVICE) as NsdManager
    }

    fun onAddService(view: View) {
        try {
            registerCall=true
            registerService()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun onScanService(view: View) {
        try {
            discoveryCall=true
            discovertServices()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun discovertServices() {
        try {
            mNsdManager.discoverServices(
                AppContants.SERVICE_TYPE,
                NsdManager.PROTOCOL_DNS_SD, MyScanListener(this)
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun registerService() {
        try {
            val serviceInfo = NsdServiceInfo()
            serviceInfo.serviceName = AppContants.SERVICE_NAME
            serviceInfo.serviceType = AppContants.SERVICE_TYPE
            serviceInfo.port = AppContants.PORT
            serviceInfo.host = InetAddress.getLocalHost()
            mNsdManager.registerService(
                serviceInfo,
                NsdManager.PROTOCOL_DNS_SD,
                MyRegistrationListener(this)
            )
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun onRegistration(msg: String) {
        registerCall=false
        showMessage(msg)
    }

    protected fun showMessage(msg: String) {
        Toast.makeText(mApplication, msg, Toast.LENGTH_LONG).show()
    }

    override fun discoveryStatus(msg: String) {
        discoveryCall=false
        showMessage(msg)
    }

    override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
        discoveryCall=false
        mNsdManager.resolveService(serviceInfo, MyServiceResolver(this))
    }

    override fun onServiceResolved(scannedData: ScannedData) {
        discoveryCall=false
        val datas = ArrayList<ScannedData>()
        datas.add(scannedData)
        data.postValue(datas)
    }


    fun stopDisCoverService() {
        mNsdManager.stopServiceDiscovery(MyScanListener(this))
    }

    fun stopService() {
        mNsdManager.unregisterService(MyRegistrationListener(this))
    }


}