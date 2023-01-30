package com.encodingideas.tic_tac_toe.service

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.encodingideas.tic_tac_toe.common.AppConstants
import com.encodingideas.tic_tac_toe.vo.Movement
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BluetoothService(private val context: Context) {

    private val movements: MutableLiveData<MutableSet<Movement>> by lazy {
        MutableLiveData<MutableSet<Movement>>(mutableSetOf())
    }

    var acceptThread: BluetoothAcceptThread? = null
    var connectThread: BluetoothConnectThread? = null
    var attachedThread: BluetoothAttachedThread? = null

    fun initializeServerMode(){
        connectThread?.cancel()
        attachedThread?.cancel()
        acceptThread?: kotlin.run {
            acceptThread = BluetoothAcceptThread(context, this::connectionHandler)
            acceptThread!!.start()
        }
    }

    fun connectToDevice(device: BluetoothDevice){
        acceptThread?.cancel()
        attachedThread?.cancel()
        connectThread?: kotlin.run {
            connectThread = BluetoothConnectThread(device, context, this::connectionHandler)
            connectThread!!.start()
        }
    }

    @Synchronized
    fun connectionHandler(socket: BluetoothSocket, isIncoming: Boolean) {
        if(isIncoming) {
            connectThread?.cancel()
        } else {
            acceptThread?.cancel()
        }
        attachedThread?: kotlin.run {
            attachedThread = BluetoothAttachedThread(socket, handler)
            attachedThread!!.start()
        }
    }

    private val handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {

            when(msg.what) {
                AppConstants.MESSAGE_WRITE -> {
                    updateMovements(msg.obj)
                }
                AppConstants.MESSAGE_READ -> {
                    updateMovements(msg.obj)
//                    val readBuf = msg.obj as ByteArray
                    //val readMessage: String = String(readBuf, 0, msg.arg1)
                }
                AppConstants.MESSAGE_TOAST ->{
                    Log.e("Bluetooth Service", msg.obj as String)
                }
            }
            //super.handleMessage(msg)
        }
    }

    fun updateMovements(obj: Any){
        val writeBuf = obj as ByteArray
        Json.decodeFromString<Movement>(String(writeBuf)).let {
            movements.value?.add(it)
        }
    }

    fun getMovementsList(): MutableLiveData<MutableSet<Movement>>{
        return movements
    }
}