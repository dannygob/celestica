package com.example.celestic.utils

import androidx.compose.runtime.compositionLocalOf

data class LocalizedStrings(
    val dashboardTitle: String = "Panel principal",
    val calibrationSection: String = "Calibración y configuración",
    val openCalibration: String = "Abrir calibración",
    val analysisModes: String = "Modos de inspección",
    val modeCarroceria: String = "Carrocería",
    val modePrecision: String = "Precisión",
    val modeMetales: String = "Metal",
    val reportsSection: String = "Reportes y historial",
    val generateReport: String = "Generar reporte",
    val viewHistory: String = "Ver historial",
    val languageSettingHint: String = "Idioma actual: Español",
    val toastOpenCalibration: String = "Navegando a calibración...",
    val toastOpenDetails: String = "Abriendo historial...",
    val toastOpenReportDialog: String = "Solicitando generación de reporte...",
    val toastModeCarroceria: String = "Mod Carrocería seleccionado",
    val toastModePrecision: String = "Modo Precisión seleccionado",
    val toastModeMetales: String = "Modo Metal seleccionado",
)

val LocalizedStrings = compositionLocalOf { LocalizedStrings() }