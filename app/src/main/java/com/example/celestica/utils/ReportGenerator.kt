package com.example.celestic.utils

import android.content.Context
import com.example.celestica.models.DetectionItem
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileOutputStream

fun generatePdfFromDetections(
    context: Context,
    detections: List<DetectionItem>,
    loteId: String,
): File {
    val file = File(context.getExternalFilesDir(null), "ReporteCelestic_$loteId.pdf")
    val writer = PdfWriter(file)
    val pdf = PdfDocument(writer)
    val document = Document(pdf)

    document.add(Paragraph("Reporte de Detecciones - Lote: $loteId"))
    detections.forEach {
        document.add(Paragraph("ID: ${it.id}"))
        document.add(Paragraph("Tipo: ${it.type}"))
        document.add(Paragraph("Confianza: ${it.confidence}"))
        document.add(Paragraph("Status: ${it.status}"))
        document.add(Paragraph("--------------------"))
    }

    document.close()
    return file
}

fun generateCsvFromDetections(
    context: Context,
    detections: List<DetectionItem>,
    loteId: String,
): File {
    val file = File(context.getExternalFilesDir(null), "ReporteCelestic_$loteId.csv")
    val writer = file.bufferedWriter()
    writer.write("ID,Tipo,Confianza,Status,Ancho (mm),Alto (mm)\n")
    detections.forEach {
        val width = it.measurementMm
        val height = it.measurementMm
        writer.write("${it.id},${it.type},${it.confidence},${it.status},${width ?: ""},${height ?: ""}\n")
    }
    writer.close()
    return file
}

fun generateWordFromDetections(
    context: Context,
    detections: List<DetectionItem>,
    loteId: String,
): File {
    val file = File(context.getExternalFilesDir(null), "ReporteCelestic_$loteId.docx")
    val document = XWPFDocument()

    val title = document.createParagraph()
    title.createRun().setText("Reporte de Detecciones - Lote: $loteId")

    detections.forEach {
        val paragraph = document.createParagraph()
        paragraph.createRun().setText("ID: ${it.id}")
        paragraph.createRun().setText("Tipo: ${it.type}")
        paragraph.createRun().setText("Confianza: ${it.confidence}")
        paragraph.createRun().setText("Status: ${it.status}")
        paragraph.createRun().setText("--------------------")
    }

    val fileOut = FileOutputStream(file)
    document.write(fileOut)
    fileOut.close()
    document.close()
    return file
}

fun exportJsonSummary(context: Context, detections: List<DetectionItem>, loteId: String): File? {
    val gson = Gson()
    val json = gson.toJson(detections)
    val file = File(context.getExternalFilesDir(null), "ReporteCelestic_$loteId.json")
    FileWriter(file).use { it.write(json) }
    return file
}
