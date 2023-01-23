package com.encodingideas.tic_tac_toe.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.encodingideas.tic_tac_toe.model.Settings
import com.encodingideas.tic_tac_toe.repository.SettingsRepository
import com.encodingideas.tic_tac_toe.ui.theme.TictactoeTheme

class Home : ComponentActivity() {
    lateinit var settings: State<Settings?>

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



    @Composable
    @Preview
    fun homeContent() {
        settings = SettingsRepository(this).settings.observeAsState(null)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = settings.value?.name ?: "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}