package com.example.aventuraturistica.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.aventuraturistica.R

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val continuar = Runnable {
        if (!isFinishing && !isDestroyed) {
            startActivity(
                Intent(
                    this@SplashActivity,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Mostrar la bienvenida antes de abrir el Login.
        handler.postDelayed(continuar, 1800L)
    }

    override fun onDestroy() {
        // Evitar ejecutar la navegación si la Activity se destruye.
        handler.removeCallbacks(continuar)
        super.onDestroy()
    }
}
