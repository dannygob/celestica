package com.example.celestica.ui.screen


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.celestic.utils.*
import com.example.celestica.models.DetectionItem
import com.example.celestica.navigation.NavigationRoutes
import com.example.celestica.utils.LocalizedStrings
import com.example.celestica.utils.exportJsonSummary
import com.example.celestica.utils.generatePdfFromDetections
import com.example.celestica.utils.generateWordFromDetections
import com.example.celestica.viewmodel.MainViewModel
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val context = LocalContext.current
    val strings = LocalizedStrings.current
    var useCharuco by remember { mutableStateOf(true) }
    val formatos = listOf("PDF", "Word", "JSON", "CSV")
    var formatoSeleccionado by remember { mutableStateOf("PDF") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Celestic Dashboard") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Text(
                text = strings.dashboardTitle,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))

            // 🔧 Sección Configuración
            Card(elevation = CardDefaults.cardElevation()) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = strings.calibrationSection,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = {
                            Toast.makeText(
                                context,
                                strings.toastOpenCalibration,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.navigate(NavigationRoutes.Calibration.route)
                        }) {
                            Text(strings.openCalibration)
                        }

                        Spacer(Modifier.width(16.dp))

                        Switch(checked = useCharuco, onCheckedChange = {
                            useCharuco = it
                            val marker = if (useCharuco) "Charuco" else "AprilTag"
                            Toast.makeText(
                                context,
                                "Marcador seleccionado: $marker",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                        Text(if (useCharuco) "Charuco" else "AprilTag")
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🚀 Sección Modalidades de inspección
            Card(elevation = CardDefaults.cardElevation()) {
                Column(Modifier.padding(16.dp)) {
                    Text(strings.analysisModes, style = MaterialTheme.typography.titleMedium)

                    Row {
                        Button(onClick = {
                            Toast.makeText(context, strings.toastModeCarroceria, Toast.LENGTH_SHORT)
                                .show()
                        }) {
                            Text(strings.modeCarroceria)
                        }

                        Spacer(Modifier.width(8.dp))

                        Button(onClick = {
                            Toast.makeText(context, strings.toastModePrecision, Toast.LENGTH_SHORT)
                                .show()
                        }) {
                            Text(strings.modePrecision)
                        }

                        Spacer(Modifier.width(8.dp))

                        Button(onClick = {
                            Toast.makeText(context, strings.toastModeMetales, Toast.LENGTH_SHORT)
                                .show()
                        }) {
                            Text(strings.modeMetales)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 📊 Sección Historial y generación de reportes
            Card(elevation = CardDefaults.cardElevation()) {
                Column(Modifier.padding(16.dp)) {
                    Text(strings.reportsSection, style = MaterialTheme.typography.titleMedium)

                    Spacer(Modifier.height(8.dp))

                    Text("Formato de exportación:", style = MaterialTheme.typography.bodyMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        formatos.forEach { formato ->
                            Button(onClick = { formatoSeleccionado = formato }) {
                                Text(formato)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        val loteId = "Lote123"
                        val detecciones = viewModel.detections.value

                        val archivo = when (formatoSeleccionado) {
                            "PDF" -> generatePdfFromDetections(
                                context,
                                detecciones as List<DetectionItem>, loteId
                            )

                            "Word" -> generateWordFromDetections(
                                context,
                                detecciones as List<DetectionItem>, loteId
                            )

                            "JSON" -> exportJsonSummary(
                                context,
                                detecciones as List<DetectionItem>, loteId
                            )

                            "CSV" -> generateCsvFromDetections(
                                context,
                                detecciones as List<DetectionItem>, loteId
                            )
                            else -> null
                        }

                        archivo?.let {
                            Toast.makeText(
                                context,
                                "Reporte generado: ${it.name}",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }) {
                        Text("Generar reporte ($formatoSeleccionado)")
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {
                        navController.navigate("detection_list")
                    }) {
                        Text("View Detections")
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🌐 Multilenguaje
            Text(
                text = strings.languageSettingHint,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}