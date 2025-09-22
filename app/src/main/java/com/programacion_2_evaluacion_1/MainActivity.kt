package com.programacion_2_evaluacion_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.programacion_2_evaluacion_1.ui.MeterApp
import com.programacion_2_evaluacion_1.ui.MeterViewModel
import com.programacion_2_evaluacion_1.ui.MeterViewModelFactory
import com.programacion_2_evaluacion_1.ui.theme.Programacion2_evaluacion1Theme

class MainActivity : ComponentActivity() {
    private val viewModel: MeterViewModel by viewModels {
        MeterViewModelFactory((application as MeterApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Programacion2_evaluacion1Theme {
                MeterApp(viewModel = viewModel)
            }
        }
    }
}
