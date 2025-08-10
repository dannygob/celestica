package com.example.celestica.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText

import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import com.example.celestica.models.calibration.DetectedFeature

@Composable
fun BlueprintView(features: List<DetectedFeature>, useInches: Boolean = false) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue.copy(alpha = 0.1f))
    ) {
        features.forEach { feature ->
            drawCircle(
                color = Color.White,
                center = Offset(feature.xCoord, feature.yCoord),
                radius = 5f,
                style = Stroke(width = 2f)
            )
            val dimension = if (useInches) {
                feature.measurements["diameter"]?.div(25.4f)
            } else {
                feature.measurements["diameter"]
            }
            dimension?.let {
                drawText(
                    textMeasurer = textMeasurer,
                    text = "%.2f".format(it),
                    style = TextStyle(color = Color.White),
                    topLeft = Offset(feature.xCoord + 10, feature.yCoord - 10)
                )
            }
        }
    }
}

@Preview
@Composable
fun BlueprintViewPreview() {
    BlueprintView(
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
