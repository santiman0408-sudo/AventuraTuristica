
package com.example.aventuraturistica.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aventuraturistica.R
import com.example.aventuraturistica.data.Reserva
import com.example.aventuraturistica.data.ReservaDbHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Locale

class CrearReservaActivity : AppCompatActivity() {

    private lateinit var tilNombre: TextInputLayout
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var tilDestino: TextInputLayout
    private lateinit var tilFecha: TextInputLayout
    private lateinit var tilPersonas: TextInputLayout
    private lateinit var tilPunto: TextInputLayout
    private lateinit var tilLatitud: TextInputLayout
    private lateinit var tilLongitud: TextInputLayout

    private lateinit var etNombre: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etDestino: TextInputEditText
    private lateinit var etFecha: TextInputEditText
    private lateinit var etPersonas: TextInputEditText
    private lateinit var etPunto: TextInputEditText
    private lateinit var etLatitud: TextInputEditText
    private lateinit var etLongitud: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_reserva)

        tilNombre = findViewById(R.id.tilNombre)
        tilTelefono = findViewById(R.id.tilTelefono)
        tilDestino = findViewById(R.id.tilDestino)
        tilFecha = findViewById(R.id.tilFecha)
        tilPersonas = findViewById(R.id.tilPersonas)
        tilPunto = findViewById(R.id.tilPunto)
        tilLatitud = findViewById(R.id.tilLatitud)
        tilLongitud = findViewById(R.id.tilLongitud)

        etNombre = findViewById(R.id.etNombre)
        etTelefono = findViewById(R.id.etTelefono)
        etDestino = findViewById(R.id.etDestino)
        etFecha = findViewById(R.id.etFecha)
        etPersonas = findViewById(R.id.etPersonas)
        etPunto = findViewById(R.id.etPunto)
        etLatitud = findViewById(R.id.etLatitud)
        etLongitud = findViewById(R.id.etLongitud)

        etFecha.setOnClickListener {
            mostrarSelectorFecha()
        }

        findViewById<MaterialButton>(R.id.btnGuardarReserva)
            .setOnClickListener {
                registrarReserva()
            }
    }

    private fun mostrarSelectorFecha() {
        val hoy = Calendar.getInstance()

        val selector = DatePickerDialog(
            this,
            { _, anio, mes, dia ->
                etFecha.setText(
                    String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        anio,
                        mes + 1,
                        dia
                    )
                )
                tilFecha.error = null
            },
            hoy.get(Calendar.YEAR),
            hoy.get(Calendar.MONTH),
            hoy.get(Calendar.DAY_OF_MONTH)
        )

        selector.datePicker.minDate = hoy.timeInMillis
        selector.show()
    }

    private fun registrarReserva() {
        limpiarErrores()

        val nombre = etNombre.text?.toString()?.trim().orEmpty()
        val telefono = etTelefono.text?.toString()?.trim().orEmpty()
        val destino = etDestino.text?.toString()?.trim().orEmpty()
        val fecha = etFecha.text?.toString()?.trim().orEmpty()
        val punto = etPunto.text?.toString()?.trim().orEmpty()

        val cantidad = etPersonas.text?.toString()
            ?.trim()?.toIntOrNull()

        val latitud = etLatitud.text?.toString()
            ?.trim()?.toDoubleOrNull()

        val longitud = etLongitud.text?.toString()
            ?.trim()?.toDoubleOrNull()

        var valido = true

        if (nombre.isBlank()) {
            tilNombre.error = "Ingresa el nombre del cliente"
            valido = false
        }

        if (telefono.filter { it.isDigit() }.length < 9) {
            tilTelefono.error = "Ingresa un teléfono válido"
            valido = false
        }

        if (destino.isBlank()) {
            tilDestino.error = "Ingresa el destino turístico"
            valido = false
        }

        if (fecha.isBlank()) {
            tilFecha.error = "Selecciona la fecha del viaje"
            valido = false
        }

        if (cantidad == null || cantidad <= 0) {
            tilPersonas.error = "La cantidad debe ser mayor que cero"
            valido = false
        }

        if (punto.isBlank()) {
            tilPunto.error = "Ingresa el punto de encuentro"
            valido = false
        }

        if (latitud == null || latitud !in -90.0..90.0) {
            tilLatitud.error = "Latitud entre -90 y 90"
            valido = false
        }

        if (longitud == null || longitud !in -180.0..180.0) {
            tilLongitud.error = "Longitud entre -180 y 180"
            valido = false
        }

        if (!valido) return

        val reserva = Reserva(
            clienteNombre = nombre,
            telefono = telefono,
            destino = destino,
            fechaViaje = fecha,
            cantidadPersonas = cantidad!!,
            estadoReserva = "PENDIENTE",
            puntoEncuentro = punto,
            latitud = latitud!!,
            longitud = longitud!!
        )

        val helper = ReservaDbHelper(this)

        try {
            val id = helper.insertar(reserva)

            if (id > 0L) {
                Toast.makeText(
                    this,
                    "Reserva registrada. Código: $id",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            } else {
                Toast.makeText(
                    this,
                    "No se pudo registrar la reserva",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error al guardar la reserva",
                Toast.LENGTH_LONG
            ).show()
        } finally {
            helper.close()
        }
    }

    private fun limpiarErrores() {
        tilNombre.error = null
        tilTelefono.error = null
        tilDestino.error = null
        tilFecha.error = null
        tilPersonas.error = null
        tilPunto.error = null
        tilLatitud.error = null
        tilLongitud.error = null
    }
}
