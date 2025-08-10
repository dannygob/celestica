package com.example.celestica.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrazabilidadItem(
    val codigo: String,
    val pieza: String,
    val operario: String,
    val fecha: String,
    val resultado: String,
) : Parcelable