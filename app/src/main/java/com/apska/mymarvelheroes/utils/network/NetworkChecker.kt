package com.apska.mymarvelheroes.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

class NetworkChecker {


    companion object {
        var isNetworkConnected = false

        fun registerNetworkCallBack(context: Context, callback: OnRegisterNetworkCallback) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object :
                    ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        isNetworkConnected = true
                        callback.onAvailable()
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        isNetworkConnected = false
                    }
                })
            } else {
                val networkRequest = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build()

                connectivityManager.registerNetworkCallback(networkRequest, object :
                    ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        isNetworkConnected = true
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        isNetworkConnected = false
                    }
                })
            }
        }
    }


}