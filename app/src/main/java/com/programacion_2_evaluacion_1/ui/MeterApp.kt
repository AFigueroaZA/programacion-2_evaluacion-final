package com.programacion_2_evaluacion_1.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.programacion_2_evaluacion_1.data.MeterType
import com.programacion_2_evaluacion_1.ui.screens.AddReadingScreen
import com.programacion_2_evaluacion_1.ui.screens.ReadingListScreen
import java.time.LocalDate

private const val LIST_ROUTE = "list"
private const val FORM_ROUTE = "form"

@Composable
fun MeterApp(viewModel: MeterViewModel) {
    val navController = rememberNavController()
    val readings by viewModel.readings.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = LIST_ROUTE) {
        composable(LIST_ROUTE) {
            ReadingListScreen(
                readings = readings,
                onAddReading = { navController.navigate(FORM_ROUTE) }
            )
        }
        composable(FORM_ROUTE) {
            AddReadingScreen(
                onBack = { navController.popBackStack() },
                onSave = { value: Int, date: LocalDate, type: MeterType ->
                    viewModel.addReading(value, date, type)
                    navController.popBackStack()
                }
            )
        }
    }
}
