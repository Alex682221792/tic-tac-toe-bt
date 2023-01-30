package com.encodingideas.tic_tac_toe.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.encodingideas.tic_tac_toe.vo.DeviceBtData

@Composable
fun ItemDeviceBt(deviceBtData: DeviceBtData, tapAction: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(all = 5.dp)
            .clickable {
                println("**** conectando a ${deviceBtData.name}")
                tapAction.invoke()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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