package com.encodingideas.tic_tac_toe.view

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import com.encodingideas.tic_tac_toe.common.BluetoothUtil
import com.encodingideas.tic_tac_toe.model.Settings
import com.encodingideas.tic_tac_toe.repository.SettingsRepository
import com.encodingideas.tic_tac_toe.ui.theme.TictactoeTheme
import com.encodingideas.tic_tac_toe.widgets.DeviceBtData
import com.encodingideas.tic_tac_toe.widgets.ItemDeviceBt

class Home : ComponentActivity() {
    lateinit var settings: State<Settings?>
    lateinit var bluetoothUtil: BluetoothUtil
    var deviceList = setOf<BluetoothDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TictactoeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    homeContent()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bluetoothUtil = BluetoothUtil(this).apply {
            setUp()
        }
        initPairedDeviceListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothUtil.REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            bluetoothUtil.fetchPairedDevices()
        }
    }


    private fun initPairedDeviceListener() {
        bluetoothUtil.pairedDevices.observe(this, Observer {
            if (it == null) {
                println("- - - - -  sin dispositivos emparejados")
            } else {
                deviceList = it
                println("* * * * * * dispositivos emparejados: ${it.size}")
            }
        })
    }


    @SuppressLint("MissingPermission")
    @Composable
    @Preview
    fun homeContent() {
        settings = SettingsRepository(this).settings.observeAsState(null)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(getString(com.encodingideas.tic_tac_toe.R.string.app_name))
                    }
                )
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .padding(it)
                        .fillMaxHeight(fraction = 0.4f)
                        .fillMaxSize()
                ) {
                    Text(
                        text = settings.value?.name ?: "",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                Box(
                    Modifier
                        .padding(it)
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = 0.95f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    LazyColumn {
                        deviceList.map {
                            DeviceBtData(
                                it.name,
                                it.address
                            )
                        }.forEach { elmData ->
                            item {
                                ItemDeviceBt(deviceBtData = elmData)
                            }
                        }
                    }
                }
            }
        }
    }
}