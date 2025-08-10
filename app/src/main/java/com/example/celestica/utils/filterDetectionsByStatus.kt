package com.example.celestica.utils


import android.content.Context
import android.os.Environment
import com.example.celestica.models.DetectionItem
import com.example.celestica.models.enums.DetectionStatus
import com.google.gson.Gson
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileOutputStream

fun filterDetectionsByStatus(
    detections: List<DetectionItem>,
    status: DetectionStatus,
): List<DetectionItem> = detections.filter { it.status == status }

fun exportJsonSummary(context: Context, detections: List<DetectionItem>, loteId: String): File {
    val json = Gson().toJson(detections)
    val dir = File(Environment.getExternalStorageDirectory(), "Celestic/Reports")
    if (!dir.exists()) dir.mkdirs()
    val file = File(dir, "ReporteCelestic_$loteId.json")
    file.writeText(json)
    return file
}

fun generatePdfFromDetections(
    context: Context,
    detections: List<DetectionItem>,
    loteId: String,
): File {
    // Puedes usar AndroidPdfDocument o PDFBox aquí (dependiendo de tu enfoque).
    // Este es un placeholder.
    val dir = File(Environment.getExternalStorageDirectory(), "Celestic/Reports")
    if (!dir.exists()) dir.mkdirs()
    val file = File(dir, "ReporteCelestic_$loteId.pdf")
    file.writeText("PDF simulado para lote $loteId con ${detections.size} inspecciones.")
    return file
}

fun generateWordFromDetections(
    context: Context,
    detections: List<DetectionItem>,
    loteId: String,
): File {
    val document = XWPFDocument()
    val paragraph = document.createParagraph()
    val run = paragraph.createRun()
    run.setText("Reporte de inspección para lote: $loteId")
    detections.forEach {
        run.addBreak()
        run.setText("🔹 ${it.type} - ${it.status} - ${it.linkedQrCode}")
    }

    val dir = File(Environment.getExternalStorageDirectory(), "Celestic/Reports")
    if (!dir.exists()) dir.mkdirs()
    val file = File(dir, "ReporteCelestic_$loteId.docx")
    val out = FileOutputStream(file)
    document.write(out)
    out.close()
    return file
}