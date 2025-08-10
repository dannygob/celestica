package com.example.celestica.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.celestica.models.DetectionItem
import com.example.celestica.models.enums.DetectionStatus
import com.example.celestica.models.enums.DetectionType
import com.example.celestica.models.geometry.BoundingBox

@Composable
fun DetectionItemCard(item: DetectionItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${item.id}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Type: ${item.type}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Status: ${item.status}")
            Spacer(modifier = Modifier.height(8.dp))
            item.measurementMm?.let {
                Text("Measurement: $it mm")
            }
        }
    }
}

@Preview
@Composable
fun DetectionItemCardPreview() {
    DetectionItemCard(
        item = DetectionItem(
            id = 1,
            inspectionId = 1,
            frameId = "frame1",
            type = DetectionType.HOLE,
            boundingBox = BoundingBox(0f, 0f, 0f, 0f),
            confidence = 0.9f,
            status = DetectionStatus.OK,
            measurementMm = 10f,
            timestamp = 1234567890,
            linkedQrCode = "qr1",
            notes = "notes1"
        )
    )
}
