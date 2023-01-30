package com.encodingideas.tic_tac_toe.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.encodingideas.tic_tac_toe.service.BluetoothService
import com.encodingideas.tic_tac_toe.ui.theme.TictactoeTheme
import com.encodingideas.tic_tac_toe.vo.Movement

class Gameboard : ComponentActivity() {
    private lateinit var bluetoothService: BluetoothService

    lateinit var movements: State<MutableSet<Movement>?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TictactoeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    board()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //bluetoothService = BluetoothService(this)
        //bluetoothService.initializeServerMode()
    }

    @Composable
    fun board() {
        movements = bluetoothService.getMovementsList().observeAsState()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            var itemsCounter = if(movements.value != null) {movements.value!!.size} else {0}
            items(itemsCounter) { movIndex ->
                Image(
                    painter = painterResource(id = movements.value!!.toList()[movIndex].value.icon),
                    contentDescription = ""
                )
            }
        }
    }
}