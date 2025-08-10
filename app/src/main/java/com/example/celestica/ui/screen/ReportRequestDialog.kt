package com.example.celestica.ui.screen

import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun ReportRequestDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(R.string.generateReport)) },
        text = { Text(text = stringResource(R.string.loading)) },
        confirmButton = {
            Button(onClick = {
                Toast.makeText(context, "Solicitando reporte...", Toast.LENGTH_SHORT).show()
                onConfirm()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(stringResource(R.string.back))
            }
        }
    )
}