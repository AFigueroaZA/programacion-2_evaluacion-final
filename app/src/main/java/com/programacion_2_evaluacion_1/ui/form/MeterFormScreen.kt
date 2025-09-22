package com.programacion_2_evaluacion_1.ui.form

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.programacion_2_evaluacion_1.R
import com.programacion_2_evaluacion_1.data.MeterType
import com.programacion_2_evaluacion_1.viewmodel.MeterInputState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MeterFormScreen(
    state: MeterInputState,
    onBack: () -> Unit,
    onValueChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onTypeChange: (MeterType) -> Unit,
    onSave: () -> Unit
) {
    val dateFormatter = remember { DateTimeFormatter.ISO_LOCAL_DATE }
    val context = LocalContext.current
    val datePickerDialog = remember(state.date) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateChange(LocalDate.of(year, month + 1, dayOfMonth))
            },
            state.date.year,
            state.date.monthValue - 1,
            state.date.dayOfMonth
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                title = { Text(text = stringResource(R.string.form_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = state.valueText,
                onValueChange = onValueChange,
                label = { Text(text = stringResource(R.string.meter_value_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (state.valueError) {
                        Text(text = stringResource(R.string.value_error_message))
                    }
                },
                isError = state.valueError,
                singleLine = true
            )
            OutlinedTextField(
                value = dateFormatter.format(state.date),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                label = { Text(text = stringResource(R.string.meter_date_label)) },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = stringResource(R.string.choose_date)
                    )
                }
            )
            Text(
                text = stringResource(R.string.meter_type_label),
                style = MaterialTheme.typography.titleMedium
            )
            MeterType.values().forEach { type ->
                MeterTypeOption(
                    type = type,
                    selected = state.selectedType == type,
                    onTypeChange = { onTypeChange(type) }
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.save_reading),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
private fun MeterTypeOption(
    type: MeterType,
    selected: Boolean,
    onTypeChange: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTypeChange() }
                .padding(vertical = 4.dp)
        ) {
            RadioButton(selected = selected, onClick = onTypeChange)
            Icon(
                painter = painterResource(type.iconRes),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(type.labelRes),
                modifier = Modifier.padding(start = 12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
