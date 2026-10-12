package com.example.aventuraturistica.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.aventuraturistica.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mapa: GoogleMap? = null

    private var latitud = -12.0464
    private var longitud = -77.0428
    private var puntoEncuentro = "Punto de encuentro"

    private val solicitudPermisos = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->

        val concedido =
            permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (concedido) {
            activarUbicacion()
        } else {
            Toast.makeText(
                this,
                "No se concedió el permiso de ubicación",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        latitud = intent.getDoubleExtra("LATITUD", -12.0464)
        longitud = intent.getDoubleExtra("LONGITUD", -77.0428)

        puntoEncuentro = intent.getStringExtra("PUNTO_ENCUENTRO")
            ?: "Punto de encuentro"

        findViewById<TextView>(R.id.tvTituloPunto).text =
            puntoEncuentro

        findViewById<TextView>(R.id.tvCoordenadasMapa).text =
            "Latitud: $latitud\nLongitud: $longitud"

        findViewById<MaterialButton>(R.id.btnMiUbicacion)
            .setOnClickListener {
                solicitarUbicacion()
            }

        val fragmento = supportFragmentManager
            .findFragmentById(R.id.mapFragment)
                as SupportMapFragment

        fragmento.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isZoomControlsEnabled = true

        val coordenadas = LatLng(latitud, longitud)

        googleMap.addMarker(
            MarkerOptions()
                .position(coordenadas)
                .title(puntoEncuentro)
                .snippet("Punto de encuentro de la reserva")
        )

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                coordenadas,
                15f
            )
        )

        if (tienePermisoUbicacion()) {
            activarUbicacion()
        }
    }

    private fun solicitarUbicacion() {
        if (tienePermisoUbicacion()) {
            activarUbicacion()
        } else {
            solicitudPermisos.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    private fun tienePermisoUbicacion(): Boolean {
        val preciso = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val aproximado = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return preciso || aproximado
    }

    private fun activarUbicacion() {
        val googleMap = mapa ?: return

        if (!tienePermisoUbicacion()) return

        try {
            googleMap.isMyLocationEnabled = true

            Toast.makeText(
                this,
                "Ubicación del dispositivo activada",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: SecurityException) {
            Toast.makeText(
                this,
                "No se pudo activar la ubicación",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
