package com.example.celestic.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.celestic.data.repository.DetectionRepository
import com.example.celestic.models.TrazabilidadItem
import com.example.celestic.utils.Result
import com.example.celestic.utils.buscarPorCodigo
import com.example.celestic.utils.cargarTrazabilidadDesdeJson
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

    private val _trazabilidadItem = MutableStateFlow<Result<TrazabilidadItem?>>(Result.Loading)
    val trazabilidadItem: StateFlow<Result<TrazabilidadItem?>> = _trazabilidadItem

    private val _features =
        MutableStateFlow<List<com.example.celestic.models.calibration.DetectedFeature>>(emptyList())
    val features: StateFlow<List<com.example.celestic.models.calibration.DetectedFeature>> =
        _features

    fun loadTrazabilidad(codigo: String) {
        viewModelScope.launch {
            try {
                val lista = cargarTrazabilidadDesdeJson(context)
                _trazabilidadItem.value = Result.Success(buscarPorCodigo(codigo, lista))
            } catch (e: Exception) {
                _trazabilidadItem.value = Result.Error(e)
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
