package com.example.suivisportsimple.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.suivisportsimple.data.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel backing the entry screen. Holds the selected activities and note for the
 * currently edited date. It loads any existing record from the repository and allows
 * toggling activities, editing the note and saving back to the database.
 */
class EntryViewModel(
    private val repository: ActivityRepository,
    private val date: LocalDate
) : ViewModel() {

    private val _selectedActivities = MutableStateFlow<Set<String>>(emptySet())
    val selectedActivities: StateFlow<Set<String>> = _selectedActivities

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note

    init {
        // Load existing record, if present, into the state flows
        viewModelScope.launch {
            repository.getRecord(date).first()?.let { record ->
                val activities = if (record.activities.isNotBlank()) {
                    record.activities.split(',').map { it.trim() }.toSet()
                } else emptySet()
                _selectedActivities.value = activities
                _note.value = record.note ?: ""
            }
        }
    }

    fun toggleActivity(activity: String) {
        _selectedActivities.value = _selectedActivities.value.toMutableSet().also { set ->
            if (set.contains(activity)) set.remove(activity) else set.add(activity)
        }
    }

    fun updateNote(newNote: String) {
        _note.value = newNote
    }

    fun saveRecord() {
        viewModelScope.launch {
            repository.saveRecord(date, _selectedActivities.value.toList(), _note.value.ifBlank { null })
        }
    }

    companion object {
        /**
         * Factory to construct an [EntryViewModel] with the required repository and date.
         */
        fun provideFactory(repository: ActivityRepository, date: LocalDate): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
                        return EntryViewModel(repository, date) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}