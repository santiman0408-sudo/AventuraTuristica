
package com.example.aventuraturistica.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReservaDbHelper(context: Context) :
    SQLiteOpenHelper(context, "aventura_turistica.db", null, 1) {

    companion object {
        private const val TABLA = "reservas"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                clienteNombre TEXT NOT NULL,
                telefono TEXT NOT NULL,
                destino TEXT NOT NULL,
                fechaViaje TEXT NOT NULL,
                cantidadPersonas INTEGER NOT NULL
                    CHECK(cantidadPersonas > 0),
                estadoReserva TEXT NOT NULL
                    CHECK(estadoReserva IN (
                        'PENDIENTE',
                        'CONFIRMADO',
                        'FINALIZADO'
                    )),
                puntoEncuentro TEXT NOT NULL,
                latitud REAL NOT NULL,
                longitud REAL NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Aquí se agregarán migraciones si cambia la estructura.
        // No borrar la tabla para evitar perder las reservas.
    }

    fun insertar(reserva: Reserva): Long {
        val valores = ContentValues().apply {
            put("clienteNombre", reserva.clienteNombre)
            put("telefono", reserva.telefono)
            put("destino", reserva.destino)
            put("fechaViaje", reserva.fechaViaje)
            put("cantidadPersonas", reserva.cantidadPersonas)
            put("estadoReserva", reserva.estadoReserva)
            put("puntoEncuentro", reserva.puntoEncuentro)
            put("latitud", reserva.latitud)
            put("longitud", reserva.longitud)
        }

        return writableDatabase.insert(TABLA, null, valores)
    }

    fun listar(): List<Reserva> {
        val reservas = mutableListOf<Reserva>()

        readableDatabase.query(
            TABLA,
            null,
            null,
            null,
            null,
            null,
            "id DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                reservas.add(cursor.toReserva())
            }
        }

        return reservas
    }

    fun obtenerPorId(id: Long): Reserva? {
        return readableDatabase.query(
            TABLA,
            null,
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.toReserva()
            } else {
                null
            }
        }
    }

    fun actualizarEstado(id: Long, estado: String): Int {
        val estadosValidos = setOf(
            "PENDIENTE",
            "CONFIRMADO",
            "FINALIZADO"
        )

        require(estado in estadosValidos) {
            "El estado de la reserva no es válido."
        }

        val valores = ContentValues().apply {
            put("estadoReserva", estado)
        }

        return writableDatabase.update(
            TABLA,
            valores,
            "id = ?",
            arrayOf(id.toString())
        )
    }

    private fun Cursor.toReserva(): Reserva {
        return Reserva(
            id = getLong(getColumnIndexOrThrow("id")),
            clienteNombre = getString(
                getColumnIndexOrThrow("clienteNombre")
            ),
            telefono = getString(
                getColumnIndexOrThrow("telefono")
            ),
            destino = getString(
                getColumnIndexOrThrow("destino")
            ),
            fechaViaje = getString(
                getColumnIndexOrThrow("fechaViaje")
            ),
            cantidadPersonas = getInt(
                getColumnIndexOrThrow("cantidadPersonas")
            ),
            estadoReserva = getString(
                getColumnIndexOrThrow("estadoReserva")
            ),
            puntoEncuentro = getString(
                getColumnIndexOrThrow("puntoEncuentro")
            ),
            latitud = getDouble(
                getColumnIndexOrThrow("latitud")
            ),
            longitud = getDouble(
                getColumnIndexOrThrow("longitud")
            )
        )
    }
}
