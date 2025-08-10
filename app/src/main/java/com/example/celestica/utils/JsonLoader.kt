package com.example.celestica.utils

import android.content.Context
import com.example.celestica.models.TrazabilityItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun cargarTrazabilidadDesdeJson(context: Context): List<TrazabilityItem> {
    val json = context.assets.open("trazabilidad.json").bufferedReader().use { it.readText() }
    val type = object : TypeToken<List<TrazabilityItem>>() {}.type
    return Gson().fromJson(json, type)
}

fun buscarPorCodigo(codigo: String, lista: List<TrazabilityItem>): TrazabilityItem? {
    return lista.firstOrNull { it.codigo == codigo }
}