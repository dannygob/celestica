package com.example.celestic.models


data class DetectionItemConTrazabilidad(
    val detectionItem: DetectionItem,
    val trazabilidad: TrazabilidadItem?,
)

fun DetectionItem.conTrazabilidad(info: TrazabilidadItem?): DetectionItemConTrazabilidad {
    return DetectionItemConTrazabilidad(this, info)
}