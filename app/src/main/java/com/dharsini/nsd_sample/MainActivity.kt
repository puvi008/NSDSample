package com.dharsini.nsd_sample

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharsini.nsd_sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


//.unregisterService(mRegistrationListener);


class MainActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var mActivityMainBinding: ActivityMainBinding
    var viewModal: ViewModal? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        mActivityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModal = ViewModelProviders.of(this).get(ViewModal::class.java)
        mActivityMainBinding.viewModal = viewModal
        viewModal?.data?.observe(this, userListUpdateObserver)
    }


    override fun onResume() {
        super.onResume()
        if (viewModal?.registerCall ?: false) {
            viewModal?.registerService()
        } else if (viewModal?.discoveryCall ?: false) {
            viewModal?.discovertServices()
        }
    }


    override fun onPause() {
        super.onPause()
        viewModal?.stopService()
        viewModal?.stopDisCoverService()
    }


    var userListUpdateObserver = object : Observer<ArrayList<ScannedData>> {
        override fun onChanged(value: ArrayList<ScannedData>?) {
            initRecyclerView(value)
        }
    }
    var mServiceAdapter: ServiceAdapter? = null
    private fun initRecyclerView(value: ArrayList<ScannedData>?) {
        if (mServiceAdapter != null && mServiceAdapter!!.itemCount > 0) {
            mServiceAdapter?.addData(value)
            return
        }
        mActivityMainBinding.rvServiceInfo.layoutManager =
            LinearLayoutManager(this)
        mServiceAdapter = ServiceAdapter(
            applicationContext = this,
            dataList = value
        )
        rvServiceInfo.adapter = mServiceAdapter
    }


}