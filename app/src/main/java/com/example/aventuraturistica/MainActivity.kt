package com.example.aventuraturistica

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aventuraturistica.activities.CrearReservaActivity
import com.example.aventuraturistica.activities.ReservasActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNuevaReserva =
            findViewById<MaterialButton>(R.id.btnNuevaReserva)

        val btnMisReservas =
            findViewById<MaterialButton>(R.id.btnMisReservas)

        btnNuevaReserva.setOnClickListener {
            startActivity(
                Intent(this, CrearReservaActivity::class.java)
            )
        }

        btnMisReservas.setOnClickListener {
            startActivity(
                Intent(this, ReservasActivity::class.java)
            )
        }
    }
}
