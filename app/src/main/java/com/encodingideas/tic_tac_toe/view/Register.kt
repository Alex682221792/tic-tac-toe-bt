package com.encodingideas.tic_tac_toe.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.encodingideas.tic_tac_toe.R
import com.encodingideas.tic_tac_toe.repository.SettingsRepository
import com.encodingideas.tic_tac_toe.ui.theme.TictactoeTheme
import com.encodingideas.tic_tac_toe.viewmodel.RegisterViewModel

class Register : ComponentActivity() {
    lateinit var registerViewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TictactoeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NickNameForm()
                }
            }
        }
        registerViewModel = RegisterViewModel(SettingsRepository(applicationContext))
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TictactoeTheme {
            NickNameForm()
        }
    }


    @Composable
    fun NickNameForm() {

        var name by remember {
            mutableStateOf("")
        }
        var buttonEnabledState by remember {
            mutableStateOf(true)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Tic Tac Toe",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 30.sp
                )
            )
            Image(
                painter = painterResource(id = R.drawable.tic_tac_toe),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                modifier = Modifier
                    .padding(all = 40.dp)
                    .rotate(degrees = 15f)
                    .size(width = 150.dp, height = 150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 40.dp)
            )
            Button(
                enabled = buttonEnabledState,
                onClick = {
                    buttonEnabledState = false
                    saveNickName(name) { buttonEnabledState = true }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            ) {
                Text(text = "Enter")
            }
        }
    }

    private fun saveNickName(name: String, onFinish: ()->Unit) {
        registerViewModel.registerNickName(name,
            onSuccess = {
                startActivity(Intent(applicationContext, Home::class.java))
                onFinish.invoke()
            }, onError = {
                Toast.makeText(applicationContext, "Error registrando nickname", Toast.LENGTH_LONG)
                onFinish.invoke()
            })
    }
}