package com.cuemby.mvvmexample.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.cuemby.mvvmexample.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) throw NoConnectivityException()
        return chain.proceed(chain.request())

    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null
                    && networkInfo.isConnected
                    && (networkInfo.type == ConnectivityManager.TYPE_WIFI
                    || networkInfo.type == ConnectivityManager.TYPE_MOBILE)
        } else {
            val network = connectivityManager.activeNetwork
            if (network != null) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                return networkCapabilities != null
                        && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            }

        }
        return false
    }
}