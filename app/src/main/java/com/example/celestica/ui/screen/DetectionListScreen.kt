package com.example.celestic.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.celestic.ui.component.DetectionItemCard
import com.example.celestic.ui.component.ShimmerDetectionItemCard
import com.example.celestic.utils.Result
import com.example.celestic.viewmodel.MainViewModel

@Composable
fun DetectionListScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val detectionsResult by viewModel.detections.collectAsState()

    LazyColumn {
        when (detectionsResult) {
            is Result.Loading -> {
                items(5) {
                    ShimmerDetectionItemCard()
                }
            }

            is Result.Success -> {
                val detections = (detectionsResult as Result.Success).data
                items(detections) { item ->
                    DetectionItemCard(item = item)
                }
            }

            is Result.Error -> {
                // TODO: Handle error
            }
        }
    }
}
