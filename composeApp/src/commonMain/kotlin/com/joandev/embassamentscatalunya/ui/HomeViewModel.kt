package com.joandev.embassamentscatalunya.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joandev.embassamentscatalunya.data.ReservoirApiModel
import com.joandev.embassamentscatalunya.data.ReservoirService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val waterDataService = ReservoirService()

    private val _reservoirLevels = MutableStateFlow<List<ReservoirApiModel>>(emptyList())
    val reservoirLevels: StateFlow<List<ReservoirApiModel>> = _reservoirLevels.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchWaterLevels() // Load data when ViewModel is created
    }

    fun fetchWaterLevels() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _reservoirLevels.value = waterDataService.getReservoirData()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data: ${e.message}"
                _reservoirLevels.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        waterDataService.close()
    }
}