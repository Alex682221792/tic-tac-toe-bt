package com.encodingideas.tic_tac_toe.common

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.lifecycle.MutableLiveData

class BluetoothUtil(private val activity: Activity) {
    val pairedDevices: MutableLiveData<Set<BluetoothDevice>?> by lazy {
        MutableLiveData<Set<BluetoothDevice>?>()
    }

    companion object {
        val REQUEST_ENABLE_BT = 100
    }

    fun setUp() {
        val bluetoothManager: BluetoothManager =
            activity.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter


        if (bluetoothAdapter == null) {
            println("BluetoothUtil - No bluetooth")
            return
        }
        checkEnabledBt(bluetoothAdapter)
    }

    @SuppressLint("MissingPermission")
    fun checkEnabledBt(bluetoothAdapter: BluetoothAdapter) {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            return
        }
        fetchPairedDevices()
    }

    @SuppressLint("MissingPermission")
    fun fetchPairedDevices() {
        val bluetoothManager: BluetoothManager =
            activity.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        val foundPairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices.value = foundPairedDevices
    }

}