package com.example.aventuraturistica.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aventuraturistica.R
import com.example.aventuraturistica.data.ReservaDbHelper
import com.google.android.material.button.MaterialButton

class DetalleReservaActivity : AppCompatActivity() {

    private var reservaId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_reserva)

        // Recibir el identificador enviado desde ReservasActivity
        reservaId = intent.getLongExtra("RESERVA_ID", -1L)

        if (reservaId <= 0L) {
            Toast.makeText(
                this,
                "Identificador de reserva no válido",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        // Configurar el botón para actualizar el estado
        findViewById<MaterialButton>(R.id.btnActualizarEstado)
            .setOnClickListener {
                mostrarOpcionesEstado()
            }

        // Consultar y mostrar la información de la reserva
        cargarDetalle()
    }

    private fun cargarDetalle() {
        val helper = ReservaDbHelper(this)

        val reserva = try {
            helper.obtenerPorId(reservaId)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No se pudo consultar la reserva",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        } finally {
            helper.close()
        }

        if (reserva == null) {
            Toast.makeText(
                this,
                "No se encontró la reserva",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        // Mostrar la información recuperada desde SQLite
        findViewById<TextView>(R.id.tvDetalleCliente).text =
            reserva.clienteNombre

        findViewById<TextView>(R.id.tvDetalleId).text =
            "Código de reserva: ${reserva.id}"

        findViewById<TextView>(R.id.tvDetalleTelefono).text =
            "Teléfono: ${reserva.telefono}"

        findViewById<TextView>(R.id.tvDetalleDestino).text =
            "Destino: ${reserva.destino}"

        findViewById<TextView>(R.id.tvDetalleFecha).text =
            "Fecha del viaje: ${reserva.fechaViaje}"

        findViewById<TextView>(R.id.tvDetallePersonas).text =
            "Cantidad de personas: ${reserva.cantidadPersonas}"

        findViewById<TextView>(R.id.tvDetalleEstado).text =
            "Estado: ${reserva.estadoReserva}"

        findViewById<TextView>(R.id.tvDetallePunto).text =
            "Punto de encuentro: ${reserva.puntoEncuentro}"

        findViewById<TextView>(R.id.tvDetalleCoordenadas).text =
            "Latitud: ${reserva.latitud}\n" +
                    "Longitud: ${reserva.longitud}"

        // Abrir Google Maps con las coordenadas de esta reserva
        findViewById<MaterialButton>(R.id.btnVerMapa)
            .setOnClickListener {

                val intent = Intent(
                    this,
                    MapActivity::class.java
                ).apply {
                    putExtra("LATITUD", reserva.latitud)
                    putExtra("LONGITUD", reserva.longitud)
                    putExtra(
                        "PUNTO_ENCUENTRO",
                        reserva.puntoEncuentro
                    )
                }

                startActivity(intent)
            }
    }

    private fun mostrarOpcionesEstado() {
        val estados = arrayOf(
            "PENDIENTE",
            "CONFIRMADO",
            "FINALIZADO"
        )

        AlertDialog.Builder(this)
            .setTitle("Seleccionar estado de reserva")
            .setItems(estados) { _, posicion ->
                actualizarEstado(estados[posicion])
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizarEstado(estado: String) {
        val helper = ReservaDbHelper(this)

        val filasActualizadas = try {
            helper.actualizarEstado(reservaId, estado)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No se pudo actualizar el estado",
                Toast.LENGTH_LONG
            ).show()

            return
        } finally {
            helper.close()
        }

        if (filasActualizadas > 0) {
            Toast.makeText(
                this,
                "Estado actualizado a $estado",
                Toast.LENGTH_SHORT
            ).show()

            // Volver a consultar SQLite para mostrar el nuevo estado
            cargarDetalle()
        } else {
            Toast.makeText(
                this,
                "No se encontró la reserva para actualizar",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

