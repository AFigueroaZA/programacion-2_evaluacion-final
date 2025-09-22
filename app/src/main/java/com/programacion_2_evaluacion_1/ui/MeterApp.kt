package com.programacion_2_evaluacion_1.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.programacion_2_evaluacion_1.viewmodel.MeterEvent
import com.programacion_2_evaluacion_1.viewmodel.MeterViewModel

private object Destinations {
    const val LIST = "list"
    const val FORM = "form"
}

@Composable
fun MeterApp(viewModel: MeterViewModel) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is MeterEvent.ShowMessage ->
                    snackbarHostState.showSnackbar(context.getString(event.messageRes))

                MeterEvent.ReadingSaved ->
                    if (navController.currentDestination?.route == Destinations.FORM) {
                        navController.popBackStack()
                    }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            addListDestination(navController, viewModel)
            addFormDestination(navController, viewModel)
        }
    }
}

private fun NavGraphBuilder.addListDestination(
    navController: NavHostController,
    viewModel: MeterViewModel
) {
    composable(Destinations.LIST) {
        val readings by viewModel.readings.collectAsStateWithLifecycle()
        MeterListScreen(
            readings = readings,
            onAddClick = { navController.navigate(Destinations.FORM) }
        )
    }
}

private fun NavGraphBuilder.addFormDestination(
    navController: NavHostController,
    viewModel: MeterViewModel
) {
    composable(Destinations.FORM) {
        val inputState by viewModel.inputState.collectAsStateWithLifecycle()
        MeterFormScreen(
            state = inputState,
            onBack = { navController.popBackStack() },
            onValueChange = viewModel::updateValue,
            onDateChange = viewModel::updateDate,
            onTypeChange = viewModel::updateType,
            onSave = viewModel::saveReading
        )
    }
}
