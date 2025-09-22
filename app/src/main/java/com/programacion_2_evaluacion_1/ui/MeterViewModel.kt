package com.programacion_2_evaluacion_1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.programacion_2_evaluacion_1.data.MeterReading
import com.programacion_2_evaluacion_1.data.MeterRepository
import com.programacion_2_evaluacion_1.data.MeterType
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val SHARING_TIMEOUT = 5_000L

class MeterViewModel(private val repository: MeterRepository) : ViewModel() {
    val readings: StateFlow<List<MeterReading>> = repository.readings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SHARING_TIMEOUT),
        initialValue = emptyList()
    )

    fun addReading(value: Int, date: LocalDate, type: MeterType) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addReading(
                MeterReading(
                    meterValue = value,
                    readingDate = date,
                    type = type
                )
            )
        }
    }
}

class MeterViewModelFactory(private val repository: MeterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
