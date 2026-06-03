package com.example.suivisportsimple.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.suivisportsimple.data.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel powering the dashboard screen. Computes aggregate statistics from all stored
 * activity records. Exposes a [DashboardUiState] that is collected by the UI.
 */
class DashboardViewModel(private val repository: ActivityRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getAllRecords().collect { records ->
                val totalDays = records.size
                val activityCounts = mutableMapOf<String, Int>()
                records.forEach { record ->
                    record.activities.split(',').map { it.trim() }.filter { it.isNotEmpty() }.forEach { activity ->
                        activityCounts[activity] = (activityCounts[activity] ?: 0) + 1
                    }
                }
                _uiState.value = DashboardUiState(totalDays = totalDays, activityCounts = activityCounts)
            }
        }
    }

    companion object {
        fun provideFactory(repository: ActivityRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                        return DashboardViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}

/**
 * Data holder for the dashboard screen. Contains the total number of days with recorded
 * activities and a map of activity names to their occurrence count.
 */
data class DashboardUiState(
    val totalDays: Int = 0,
    val activityCounts: Map<String, Int> = emptyMap()
)