package com.encodingideas.tic_tac_toe.vo

import android.bluetooth.BluetoothDevice


data class DeviceBtData(
    val name: String,
    val mac: String,
    val device: BluetoothDevice
)