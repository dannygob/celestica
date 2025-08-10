package com.example.celestic.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CalibrationScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.openCalibration),
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = {
            Toast.makeText(context, "Calibrando...", Toast.LENGTH_SHORT).show()
            // Acción de calibración manual
        }) {
            Text("Iniciar calibración")
        }
    }
}