package com.encodingideas.tic_tac_toe.service

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import com.encodingideas.tic_tac_toe.common.AppConstants
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class BluetoothAttachedThread(
    private val mSocket: BluetoothSocket,
    private val handler: Handler
) : Thread() {
    private val TAG = "BluetoothAttachedThread"

    private val inputStream: InputStream = mSocket.inputStream
    private val outputStream: OutputStream = mSocket.outputStream
    private val buffer: ByteArray = ByteArray(1024)

    override fun run() {
        println("* * * * * conectado y escuchando")
        var numBytes: Int
        while (true) {
            numBytes = try {
                inputStream.read(buffer)
            } catch (e: IOException) {
                Log.d(TAG, "Input stream was disconnected", e)
                break
            }

            handler.obtainMessage(
                AppConstants.MESSAGE_READ,
                numBytes,
                -1,
                buffer
            ).sendToTarget()

        }
    }

    fun write(bytes: ByteArray) {
        try {
            outputStream.write(bytes)
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)
            handler.obtainMessage(
                AppConstants.MESSAGE_TOAST,
                "Error occurred when sending data: ${e.message}"
            ).sendToTarget()
        }
        handler.obtainMessage(
            AppConstants.MESSAGE_WRITE,
            -1,
            -1,
            bytes
        ).sendToTarget()
    }

    fun cancel() {
        try {
            mSocket.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }
}