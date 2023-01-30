package com.encodingideas.tic_tac_toe.service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import com.encodingideas.tic_tac_toe.R
import com.encodingideas.tic_tac_toe.common.AppConstants
import java.io.IOException
import java.util.*

@SuppressLint("MissingPermission")
class BluetoothAcceptThread(
    private val context: Context,
    val connectionHandler: (BluetoothSocket, Boolean) -> Unit
) : Thread() {
    private val TAG = "BluetoothAcceptThread"

    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {

        val bluetoothManager: BluetoothManager =
            context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter


        if (bluetoothAdapter == null) {
            println("BluetoothAcceptThread - No bluetooth")
        }
        bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord(
            context.getString(R.string.app_name), UUID.fromString(AppConstants.BT_UUID)
        )
    }

    override fun run() {
        // Keep listening until exception occurs or a socket is returned.
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                println("- - - -- -- conexión entrante")
                mmServerSocket?.accept()
            } catch (e: IOException) {
                Log.e(TAG, "Socket's accept() method failed", e)
                shouldLoop = false
                null
            }
            socket?.also {
                println("- - - -- -- preparando la conexion")
                connectionHandler(it, true)
                mmServerSocket?.close()
                shouldLoop = false
            }
        }
    }

    fun cancel() {
        try {
            mmServerSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }
}