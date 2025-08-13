package com.programacion_2_evaluacion_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.programacion_2_evaluacion_1.model.CuentaMesa
import com.programacion_2_evaluacion_1.model.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val formatoMoneda: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
        maximumFractionDigits = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pastel = ItemMenu("Pastel de Choclo", 12000)
        val cazuela = ItemMenu("Cazuela", 10000)
        val cuenta = CuentaMesa(1).apply {
            agregarItem(pastel, 0)
            agregarItem(cazuela, 0)
        }

        val edtPastel = findViewById<EditText>(R.id.edtPastel)
        val edtCazuela = findViewById<EditText>(R.id.edtCazuela)
        val switchPropina = findViewById<Switch>(R.id.switchPropina)
        val txtSubtotalPastel = findViewById<TextView>(R.id.txtSubtotalPastel)
        val txtSubtotalCazuela = findViewById<TextView>(R.id.txtSubtotalCazuela)
        val txtTotalSinPropina = findViewById<TextView>(R.id.txtTotalSinPropina)
        val txtPropina = findViewById<TextView>(R.id.txtPropina)
        val txtTotalConPropina = findViewById<TextView>(R.id.txtTotalConPropina)

        fun actualizarPantalla() {
            cuenta.items[0].cantidad = edtPastel.text.toString().toIntOrNull() ?: 0
            cuenta.items[1].cantidad = edtCazuela.text.toString().toIntOrNull() ?: 0
            cuenta.aceptaPropina = switchPropina.isChecked

            txtSubtotalPastel.text = formatoMoneda.format(cuenta.items[0].calcularSubtotal())
            txtSubtotalCazuela.text = formatoMoneda.format(cuenta.items[1].calcularSubtotal())
            txtTotalSinPropina.text = "Total: ${formatoMoneda.format(cuenta.calcularTotalSinPropina())}"
            txtPropina.text = "Propina: ${formatoMoneda.format(cuenta.calcularPropina())}"
            txtTotalConPropina.text = "Total con propina: ${formatoMoneda.format(cuenta.calcularTotalConPropina())}"
        }

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { actualizarPantalla() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        edtPastel.addTextChangedListener(watcher)
        edtCazuela.addTextChangedListener(watcher)
        switchPropina.setOnCheckedChangeListener { _, _ -> actualizarPantalla() }

        actualizarPantalla()
    }
}
