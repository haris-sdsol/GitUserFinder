package com.seattle.gituserfinder.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class MyApplication : Application() {

    companion object {
        var instance: MyApplication? = null
            private set

        fun hasNetwork(): Boolean {
            return instance!!.checkIfHasNetwork()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    fun checkIfHasNetwork(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}