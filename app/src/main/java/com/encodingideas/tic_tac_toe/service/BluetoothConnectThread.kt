package com.encodingideas.tic_tac_toe.service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import com.encodingideas.tic_tac_toe.common.AppConstants
import java.io.IOException
import java.util.*

@SuppressLint("MissingPermission")
class BluetoothConnectThread(
    device: BluetoothDevice,
    private val context: Context,
    val connectionHandler: (BluetoothSocket, Boolean) -> Unit
) : Thread() {
    private val TAG = "BluetoothAcceptThread"

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        device.createRfcommSocketToServiceRecord(UUID.fromString(AppConstants.BT_UUID))
    }

    override fun run() {
        val bluetoothManager: BluetoothManager =
            context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
        if (bluetoothAdapter == null) {
            println("BluetoothAcceptThread - No bluetooth")
        }
//        bluetoothAdapter?.cancelDiscovery()

        mmSocket?.let {
            it.connect()
            connectionHandler(it, false)
        }
    }

    fun cancel() {
        try {
            mmSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the client socket", e)
        }
    }
}