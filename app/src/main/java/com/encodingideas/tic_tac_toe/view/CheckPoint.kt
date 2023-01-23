package com.encodingideas.tic_tac_toe.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.encodingideas.tic_tac_toe.R
import com.encodingideas.tic_tac_toe.repository.SettingsRepository
import com.encodingideas.tic_tac_toe.ui.theme.TictactoeTheme

class CheckPoint : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TictactoeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH) }?.also {
            Toast.makeText(this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show()
            finish()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val requestPermissionLauncher =
                    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                        if (isGranted) {
                            checkUserData()
                        } else {
                            // Explain to the user that the feature is unavailable because the
                            // feature requires a permission that the user has denied. At the
                            // same time, respect the user's decision. Don't link to system
                            // settings in an effort to convince the user to change their
                            // decision.
                        }
                    }
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
                return;
            }
        }
        checkUserData()
    }

    private fun checkUserData() {
        SettingsRepository(this).settings.observe(this, Observer {
            Handler().postDelayed({
                if (it == null) {
                    startActivity(Intent(this, Register::class.java))
                } else {
                    startActivity(Intent(this, Home::class.java))

                }
            }, 1000)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)
}

@Composable
@Preview
fun Content() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.75f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.tic_tac_toe),
                contentDescription = "logo",
                modifier = Modifier
                    .size(width = 75.dp, height = 75.dp)
                    .align(Alignment.Center)
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .size(width = 150.dp, height = 150.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = "Verifying configurations...",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}