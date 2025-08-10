package com.example.celestica.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.example.celestica.models.calibration.DetectedFeature

@Composable
fun DrawingCanvas(features: List<DetectedFeature>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        features.forEach { feature ->
            drawCircle(
                color = Color.Red,
                center = Offset(feature.xCoord, feature.yCoord),
                radius = 5f,
                style = Stroke(width = 2f)
            )
        }
    }
}

@Preview
@Composable
fun DrawingCanvasPreview() {
    DrawingCanvas(
        features = listOf(
            DetectedFeature(
                id = 1,
                detectionItemId = 1,
                featureType = "type",
                xCoord = 100f,
                yCoord = 100f,
                confidence = 0.9f,
                timestamp = 1234567890
            ),
            DetectedFeature(
                id = 2,
                detectionItemId = 1,
                featureType = "type",
                xCoord = 200f,
                yCoord = 200f,
                confidence = 0.9f,
                timestamp = 1234567890
            )
        )
    )
}
