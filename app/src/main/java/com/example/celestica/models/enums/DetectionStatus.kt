package com.example.celestica.models.enums

/**
 * Estado técnico de la detección basado en validación dimensional.
 * 🟢 OK: Dentro de tolerancia
 * 🟡 WARNING: Fuera de tolerancia pero no crítico
 * 🔴 NOT_ACCEPTED: Inválido o defecto crítico
 */
enum class DetectionStatus {
    OK,           // 🟢 Válido
    WARNING,      // 🟡 Advertencia
    NOT_ACCEPTED  // 🔴 No aceptado
}