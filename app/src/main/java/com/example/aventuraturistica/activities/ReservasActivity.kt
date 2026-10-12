package com.example.aventuraturistica.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aventuraturistica.R
import com.example.aventuraturistica.adapters.ReservaAdapter
import com.example.aventuraturistica.data.ReservaDbHelper

class ReservasActivity : AppCompatActivity() {

    private lateinit var rvReservas: RecyclerView
    private lateinit var tvSinReservas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        // Vincular los componentes del XML
        rvReservas = findViewById(R.id.rvReservas)
        tvSinReservas = findViewById(R.id.tvSinReservas)

        // Mostrar las reservas en una lista vertical
        rvReservas.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        // Recargar los datos al regresar de otra pantalla
        cargarReservas()
    }

    private fun cargarReservas() {
        val helper = ReservaDbHelper(this)

        val reservas = try {
            helper.listar()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No se pudieron consultar las reservas",
                Toast.LENGTH_LONG
            ).show()

            return
        } finally {
            helper.close()
        }

        // Mostrar el mensaje cuando la lista esté vacía
        val hayReservas = reservas.isNotEmpty()

        tvSinReservas.visibility =
            if (hayReservas) View.GONE else View.VISIBLE

        rvReservas.visibility =
            if (hayReservas) View.VISIBLE else View.GONE

        // Conectar SQLite con el RecyclerView
        rvReservas.adapter = ReservaAdapter(reservas) { reserva ->

            // Abrir el detalle de la reserva seleccionada
            val intent = Intent(
                this,
                DetalleReservaActivity::class.java
            )

            // Enviar el ID para consultar el registro en SQLite
            intent.putExtra("RESERVA_ID", reserva.id)

            startActivity(intent)
        }
    }
}
