package com.aaronhenehan.giphygifloader.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class NetworkManager {
    companion object {
        fun isNetworkConnected(activity: Activity): Boolean {
            val connectivityManager: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null
        }
    }
}