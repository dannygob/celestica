package com.example.celestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.celestic.data.repository.DetectionRepository
import com.example.celestic.models.DetectionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DetectionRepository,
) : ViewModel() {

    val detections: StateFlow<com.example.celestic.utils.Result<List<DetectionItem>>> =
        repository.getAll()
            .map<List<DetectionItem>, com.example.celestic.utils.Result<List<DetectionItem>>> {
                com.example.celestic.utils.Result.Success(it)
            }
            .catch {
                emit(com.example.celestic.utils.Result.Error(it as Exception))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = com.example.celestic.utils.Result.Loading
            )
}