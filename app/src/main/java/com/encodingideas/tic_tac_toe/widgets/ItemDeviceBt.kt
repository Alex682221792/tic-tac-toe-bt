package com.encodingideas.tic_tac_toe.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemDeviceBt(deviceBtData: DeviceBtData) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(all = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = deviceBtData.name,
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 20.sp),
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "(${deviceBtData.mac})",
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}

data class DeviceBtData(
    val name: String,
    val mac: String
)