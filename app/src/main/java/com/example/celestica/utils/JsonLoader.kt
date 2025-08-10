package com.example.celestica.utils

import android.content.Context
import com.example.celestica.models.TrazabilidadItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun cargarTrazabilidadDesdeJson(context: Context): List<TrazabilidadItem> {
    val json = context.assets.open("trazabilidad.json").bufferedReader().use { it.readText() }
    val type = object : TypeToken<List<TrazabilidadItem>>() {}.type
    return Gson().fromJson(json, type)
}

fun buscarPorCodigo(codigo: String, lista: List<TrazabilidadItem>): TrazabilidadItem? {
    return lista.firstOrNull { it.codigo == codigo }
}