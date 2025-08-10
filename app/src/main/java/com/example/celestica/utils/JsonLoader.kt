package com.example.celestic.utils

import android.content.Context
import com.example.celestic.models.TrazabilidadItem
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson


fun cargarTrazabilidadDesdeJson(context: Context): List<TrazabilidadItem> {
    val json = context.assets.open("trazabilidad.json").bufferedReader().use { it.readText() }
    val type = object : TypeToken<List<TrazabilidadItem>>() {}.type
    return Gson().fromJson(json, type)
}

fun buscarPorCodigo(codigo: String, lista: List<TrazabilidadItem>): TrazabilidadItem? {
    return lista.firstOrNull { it.codigo == codigo }
}