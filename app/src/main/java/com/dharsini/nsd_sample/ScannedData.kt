package com.dharsini.nsd_sample

import java.net.InetAddress

data class ScannedData (
    var serviceType:String,
    var serviceName:String,
    var port:Int,
    var hostAddress:InetAddress?,
    var hostAddressValue:String?
)
