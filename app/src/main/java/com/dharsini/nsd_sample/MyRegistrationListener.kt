package com.dharsini.nsd_sample

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

interface RegistrationListener {
    fun onRegistration(msg: String)
}

class MyRegistrationListener(val registrationListener: RegistrationListener) :
    NsdManager.RegistrationListener {


    override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        registrationListener.onRegistration("Unregistration failed")
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo?) {
        registrationListener.onRegistration("Service unregistered")
    }

    override fun onRegistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        registrationListener.onRegistration("Registration failed")
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo?) {
        registrationListener.onRegistration("Service registered")
    }

}