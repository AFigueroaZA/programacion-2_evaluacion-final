package com.programacion_2_evaluacion_1.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.programacion_2_evaluacion_1.R
import com.programacion_2_evaluacion_1.data.MeterType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReadingScreen(
    onBack: () -> Unit,
    onSave: (Int, LocalDate, MeterType) -> Unit,
    modifier: Modifier = Modifier
) {
    var meterValue by rememberSaveable { mutableStateOf("") }
    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var selectedType by rememberSaveable { mutableStateOf(MeterType.WATER) }
    var showDatePicker by remember { mutableStateOf(false) }
    var valueError by remember { mutableStateOf(false) }

    val formattedDate = remember(selectedDate) {
        selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.toEpochMillis())
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            selectedDate = millis.toLocalDate()
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.form_date_picker_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = stringResource(R.string.form_date_picker_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.form_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.form_cancel_button)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = meterValue,
                onValueChange = { value ->
                    meterValue = value.filter { it.isDigit() }
                    valueError = false
                },
                label = { Text(text = stringResource(R.string.form_meter_value_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = valueError,
                supportingText = {
                    if (valueError) {
                        Text(text = stringResource(R.string.form_value_error))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = formattedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(R.string.form_meter_date_label)) },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = stringResource(R.string.form_select_date)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }
            )

            Column {
                Text(
                    text = stringResource(R.string.form_meter_type_label),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                MeterType.values().forEach { type ->
                    RowWithRadioButton(
                        type = type,
                        isSelected = selectedType == type,
                        onSelect = { selectedType = type }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val value = meterValue.toIntOrNull()
                    if (value != null) {
                        onSave(value, selectedDate, selectedType)
                    } else {
                        valueError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.form_save_button))
            }
        }
    }
}

@Composable
private fun RowWithRadioButton(
    type: MeterType,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = type.labelRes), style = MaterialTheme.typography.bodyLarge)
    }
}

private fun LocalDate.toEpochMillis(): Long =
    this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

private fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
