package com.example.celestic.models.geometry

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Representa el área delimitadora de una característica detectada
 * en coordenadas relativas (pixeles o escala normalizada).
 */
@Parcelize
data class BoundingBox(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
) : Parcelable {
    fun width(): Float = right - left
    fun height(): Float = bottom - top
}