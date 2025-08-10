package com.example.celestic.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.celestic.ui.screen.CalibrationScreen
import com.example.celestic.ui.screen.CameraScreen
import com.example.celestic.ui.screen.DashboardScreen
import com.example.celestic.ui.screen.DetailsScreen
import com.example.celestic.ui.screen.DetectionListScreen
import com.example.celestic.ui.screen.InspectionPreviewScreen
import com.example.celestic.ui.screen.LoginScreen
import com.example.celestic.ui.screen.ReportRequestDialog
import com.example.celestic.ui.screen.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable(NavigationRoutes.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(NavigationRoutes.Camera.route) {
            CameraScreen(navController)
        }

        composable(
            NavigationRoutes.Details.route,
            arguments = listOf(navArgument("detailType") { type = NavType.StringType })
        ) { backStackEntry ->
            val detailType = backStackEntry.arguments?.getString("detailType") ?: "hole"
            DetailsScreen(navController, detailType)
        }

        composable(NavigationRoutes.Calibration.route) {
            CalibrationScreen(navController)
        }

        composable(NavigationRoutes.ReportDialog.route) {
            ReportRequestDialog(
                onDismiss = { navController.popBackStack() },
                onConfirm = { navController.popBackStack() } // lógica real si quieres enviar algo
            )
        }

        composable(NavigationRoutes.Preview.route) {
            InspectionPreviewScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        composable("detection_list") {
            DetectionListScreen(navController)
        }
    }
}