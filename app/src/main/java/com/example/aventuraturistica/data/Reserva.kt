package com.example.aventuraturistica.data

data class Reserva(
    val id: Long = 0L,
    val clienteNombre: String,
    val telefono: String,
    val destino: String,
    val fechaViaje: String,
    val cantidadPersonas: Int,
    val estadoReserva: String = "PENDIENTE",
    val puntoEncuentro: String,
    val latitud: Double,
    val longitud: Double
)

