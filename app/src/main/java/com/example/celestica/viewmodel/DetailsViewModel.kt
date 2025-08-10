package com.example.celestica.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.celestica.data.repository.DetectionRepository
import com.example.celestica.models.TrazabilityItem
import com.example.celestica.models.calibration.DetectedFeature
import com.example.celestica.utils.Result
import com.example.celestica.utils.buscarPorCodigo
import com.example.celestica.utils.cargarTrazabilidadDesdeJson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetectionRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _trazabilityItem = MutableStateFlow<Result<TrazabilityItem?>>(Result.Loading)
    val trazabilityItem: StateFlow<Result<TrazabilityItem?>> = _trazabilityItem

    private val _features =
        MutableStateFlow<List<DetectedFeature>>(emptyList())
    val features: StateFlow<List<DetectedFeature>> =
        _features

    fun loadTrazabilidad(codigo: String) {
        viewModelScope.launch {
            try {
                val lista = cargarTrazabilidadDesdeJson(context)
                _trazabilityItem.value = Result.Success(buscarPorCodigo(codigo, lista))
            } catch (e: Exception) {
                _trazabilityItem.value = Result.Error(e)
            }
        }
    }

    fun loadFeatures(detectionItemId: Long) {
        viewModelScope.launch {
            repository.getFeaturesForDetection(detectionItemId).collect {
                _features.value = it
            }
        }
    }
}
