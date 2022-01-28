package com.apska.mymarvelheroes.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService

class NetworkUtils {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.getActiveNetwork()
            } else {
                cm.activeNetworkInfo
            }

            val caps = cm.getNetworkCapabilities(networkInfo as Network?)
            //val linkProperties = cm.getLinkProperties(networkInfo)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
            } else {
                caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            }
        }



    }
}