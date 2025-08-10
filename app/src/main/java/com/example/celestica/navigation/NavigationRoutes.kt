package com.example.celestica.navigation


sealed class NavigationRoutes(val route: String) {
    object Dashboard : NavigationRoutes("dashboard")
    object Camera : NavigationRoutes("camera")
    object Details : NavigationRoutes("details/{detailType}") {
        fun createRoute(detailType: String) = "details/$detailType"
    }

    object Calibration : NavigationRoutes("calibration")
    object ReportDialog : NavigationRoutes("report_dialog")
    object Preview : NavigationRoutes("inspection_preview")
}
