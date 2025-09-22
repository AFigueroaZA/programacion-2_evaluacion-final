package com.programacion_2_evaluacion_1.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.programacion_2_evaluacion_1.R
import com.programacion_2_evaluacion_1.data.MeterReading
import com.programacion_2_evaluacion_1.data.MeterRepository
import com.programacion_2_evaluacion_1.data.MeterType
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Estado que representa el formulario de ingreso de lecturas.
 */
data class MeterInputState(
    val valueText: String = "",
    val date: LocalDate = LocalDate.now(),
    val selectedType: MeterType = MeterType.WATER,
    val valueError: Boolean = false
)

/**
 * Eventos de una sola emisión que la interfaz puede observar.
 */
sealed interface MeterEvent {
    data class ShowMessage(@StringRes val messageRes: Int) : MeterEvent
    data object ReadingSaved : MeterEvent
}

class MeterViewModel(private val repository: MeterRepository) : ViewModel() {

    val readings: StateFlow<List<MeterReading>> = repository.readings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _inputState = MutableStateFlow(MeterInputState())
    val inputState: StateFlow<MeterInputState> = _inputState.asStateFlow()

    private val _events = MutableSharedFlow<MeterEvent>()
    val events = _events.asSharedFlow()

    fun updateValue(value: String) {
        _inputState.update { it.copy(valueText = value, valueError = false) }
    }

    fun updateDate(date: LocalDate) {
        _inputState.update { it.copy(date = date) }
    }

    fun updateType(type: MeterType) {
        _inputState.update { it.copy(selectedType = type) }
    }

    fun saveReading() {
        val current = _inputState.value
        val value = current.valueText.toDoubleOrNull()
        if (value == null) {
            _inputState.update { it.copy(valueError = true) }
            viewModelScope.launch {
                _events.emit(MeterEvent.ShowMessage(R.string.value_error_message))
            }
            return
        }

        viewModelScope.launch {
            repository.addReading(
                MeterReading(
                    type = current.selectedType,
                    value = value,
                    date = current.date
                )
            )
            _inputState.value = MeterInputState(
                selectedType = current.selectedType,
                date = LocalDate.now()
            )
            _events.emit(MeterEvent.ShowMessage(R.string.reading_saved_message))
            _events.emit(MeterEvent.ReadingSaved)
        }
    }
}

class MeterViewModelFactory(private val repository: MeterRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeterViewModel::class.java)) {
            return MeterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
