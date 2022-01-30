package com.apska.mymarvelheroes.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.activeNetwork
            } else {
                cm.activeNetworkInfo
            }

            val caps = cm.getNetworkCapabilities(activeNetwork as Network?)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
            } else {
                caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            }
        }



    }
}