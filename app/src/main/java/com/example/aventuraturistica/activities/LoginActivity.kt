package com.example.aventuraturistica.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aventuraturistica.MainActivity
import com.example.aventuraturistica.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilCorreo: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    companion object {
        private const val CORREO_DEMO =
            "admin@aventuraturistica.com"

        private const val PASSWORD_DEMO =
            "Aventura123"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tilCorreo = findViewById(R.id.tilCorreo)
        tilPassword = findViewById(R.id.tilPassword)

        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)

        val btnIniciarSesion =
            findViewById<MaterialButton>(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener {
            validarCredenciales()
        }
    }

    private fun validarCredenciales() {
        val correo = etCorreo.text
            ?.toString()
            ?.trim()
            .orEmpty()

        val password = etPassword.text
            ?.toString()
            .orEmpty()

        // Validación visual del correo.
        if (correo.isBlank()) {
            tilCorreo.error = "Ingresa tu correo electrónico"
            etCorreo.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.error = "Ingresa un correo válido"
            etCorreo.requestFocus()
            return
        }

        tilCorreo.error = null

        // Validación visual de la contraseña.
        if (password.isBlank()) {
            tilPassword.error = "Ingresa tu contraseña"
            etPassword.requestFocus()
            return
        }

        tilPassword.error = null

        // Validación local para la demostración académica.
        val credencialesCorrectas =
            correo.equals(CORREO_DEMO, ignoreCase = true) &&
                    password == PASSWORD_DEMO

        if (credencialesCorrectas) {
            Toast.makeText(
                this,
                "Bienvenido a AventuraTurística",
                Toast.LENGTH_SHORT
            ).show()

            // Abrir la pantalla principal existente.
            startActivity(
                Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
            )

            // Evitar regresar al Login pulsando Atrás.
            finish()

        } else {
            tilPassword.error = "Correo o contraseña incorrectos"

            Toast.makeText(
                this,
                "Revisa tus credenciales de demostración",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
