package es.usj.mastertsa.cuidameapp.data.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import es.usj.mastertsa.cuidameapp.domain.ConnectivityService

class ConnectivityServiceImpl(private val context: Context): ConnectivityService {
    override fun checkConnectivity(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}