package com.example.aventuraturistica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aventuraturistica.R
import com.example.aventuraturistica.data.Reserva

class ReservaAdapter(
    private val reservas: List<Reserva>,
    private val onClick: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {

    class ReservaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvCliente: TextView =
            itemView.findViewById(R.id.tvCliente)

        val tvDestino: TextView =
            itemView.findViewById(R.id.tvDestino)

        val tvFecha: TextView =
            itemView.findViewById(R.id.tvFecha)

        val tvPersonas: TextView =
            itemView.findViewById(R.id.tvPersonas)

        val tvEstado: TextView =
            itemView.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservaViewHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserva, parent, false)

        return ReservaViewHolder(vista)
    }

    override fun onBindViewHolder(
        holder: ReservaViewHolder,
        position: Int
    ) {
        val reserva = reservas[position]

        holder.tvCliente.text =
            "${reserva.clienteNombre} · Reserva #${reserva.id}"

        holder.tvDestino.text =
            "Destino: ${reserva.destino}"

        holder.tvFecha.text =
            "Fecha del viaje: ${reserva.fechaViaje}"

        holder.tvPersonas.text =
            "Personas: ${reserva.cantidadPersonas}"

        holder.tvEstado.text = reserva.estadoReserva

        holder.itemView.setOnClickListener {
            onClick(reserva)
        }
    }

    override fun getItemCount(): Int = reservas.size
}
