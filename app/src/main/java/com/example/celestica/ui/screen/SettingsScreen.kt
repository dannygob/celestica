package com.example.celestica.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.celestica.viewmodel.MarkerType
import com.example.celestica.viewmodel.SharedViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel(),
) {
    var isDarkTheme by remember { mutableStateOf(false) }
    val useInches by viewModel.useInches.collectAsState()
    val markerType by viewModel.markerType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings")
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark Theme")
            Spacer(modifier = Modifier.weight(1.0f))
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Use Inches")
            Spacer(modifier = Modifier.weight(1.0f))
            Switch(
                checked = useInches,
                onCheckedChange = { viewModel.setUseInches(it) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Use AprilTag")
            Spacer(modifier = Modifier.weight(1.0f))
            Switch(
                checked = markerType == MarkerType.APRILTAG,
                onCheckedChange = {
                    viewModel.setMarkerType(if (it) MarkerType.APRILTAG else MarkerType.ARUCO)
                }
            )
        }
    }
}
