package com.example.celestic.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class MarkerType {
    ARUCO,
    APRILTAG
}

class SharedViewModel : ViewModel() {
    private val _useInches = MutableStateFlow(false)
    val useInches: StateFlow<Boolean> = _useInches

    private val _markerType = MutableStateFlow(MarkerType.ARUCO)
    val markerType: StateFlow<MarkerType> = _markerType

    fun setUseInches(useInches: Boolean) {
        _useInches.value = useInches
    }

    fun setMarkerType(markerType: MarkerType) {
        _markerType.value = markerType
    }
}
