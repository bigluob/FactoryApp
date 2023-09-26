package com.camc.media.utils


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi

object DeviceUtils {
    @SuppressLint("HardwareIds")
    fun getDeviceVersion(): String {
        return Build.VERSION.RELEASE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("HardwareIds")
    fun getMacAddress(context: Context): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
            val wifiInfo = wifiManager.connectionInfo
            return wifiInfo.macAddress ?: ""
        }
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds")
    fun getSerialNumber(): String {
        return Build.getSerial()
    }
}