package com.programacion_2_evaluacion_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.programacion_2_evaluacion_1.data.MeterDatabase
import com.programacion_2_evaluacion_1.data.MeterRepository
import com.programacion_2_evaluacion_1.ui.MeterApp
import com.programacion_2_evaluacion_1.ui.theme.Programacion2_evaluacion1Theme
import com.programacion_2_evaluacion_1.viewmodel.MeterViewModel
import com.programacion_2_evaluacion_1.viewmodel.MeterViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MeterDatabase.getDatabase(applicationContext)
        val repository = MeterRepository(database.meterReadingDao())
        val factory = MeterViewModelFactory(repository)

        setContent {
            Programacion2_evaluacion1Theme {
                val viewModel: MeterViewModel = viewModel(factory = factory)
                MeterApp(viewModel = viewModel)
            }
        }
    }
}
