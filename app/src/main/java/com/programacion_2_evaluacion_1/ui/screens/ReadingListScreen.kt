package com.programacion_2_evaluacion_1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.programacion_2_evaluacion_1.R
import com.programacion_2_evaluacion_1.data.MeterReading
import com.programacion_2_evaluacion_1.data.MeterType
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingListScreen(
    readings: List<MeterReading>,
    onAddReading: () -> Unit,
    modifier: Modifier = Modifier
) {
    val numberFormat = remember {
        NumberFormat.getIntegerInstance(Locale("es", "CL"))
    }
    val dateFormatter = remember { DateTimeFormatter.ISO_LOCAL_DATE }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.list_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddReading) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_reading)
                )
            }
        }
    ) { padding ->
        if (readings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.list_empty_message))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(readings) { reading ->
                    MeterReadingCard(
                        reading = reading,
                        dateFormatter = dateFormatter,
                        numberFormat = numberFormat
                    )
                }
            }
        }
    }
}

@Composable
private fun MeterReadingCard(
    reading: MeterReading,
    dateFormatter: DateTimeFormatter,
    numberFormat: NumberFormat,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = meterTypeIcon(reading.type),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = reading.type.labelRes),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        R.string.list_item_date,
                        dateFormatter.format(reading.readingDate)
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = numberFormat.format(reading.meterValue),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun meterTypeIcon(type: MeterType) = when (type) {
    MeterType.WATER -> Icons.Outlined.WaterDrop
    MeterType.ELECTRICITY -> Icons.Outlined.Bolt
    MeterType.GAS -> Icons.Outlined.LocalFireDepartment
}
