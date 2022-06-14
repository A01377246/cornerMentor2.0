package mx.itesm.cornermentor20.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.cornermentor20.Asesoria
import mx.itesm.cornermentor20.ListenerRecycler
import mx.itesm.cornermentor20.R

//View Holder para las asesorías que serán descargadas de la base de datos

class AdaptadorAsesoria (private val contexto: Context, var arrAsesorias: Array<Asesoria>) :
    RecyclerView.Adapter<AdaptadorAsesoria.RenglonAsesoria>() {

    var listener : ListenerRecycler? = null  //? permite recibir un null

    class RenglonAsesoria (var vistaRenglon: View) : RecyclerView.ViewHolder(vistaRenglon){

        fun set(asesoria: Asesoria){
            // Poner en los textView algunos elementos (importantes para el usuario) de asesoria
            //vistaRenglon.setBackgroundColor(Color.parseColor("#FFFFFF"))
            vistaRenglon.findViewById<TextView>(R.id.tvMentor).text = "Mentor: ${asesoria.mentor}"
            vistaRenglon.findViewById<TextView>(R.id.tvMentor).setTextColor(Color.parseColor("#000000"))
            vistaRenglon.findViewById<TextView>(R.id.tvFecha).text = "Fecha: ${asesoria.fecha}"
            vistaRenglon.findViewById<TextView>(R.id.tvFecha).setTextColor(Color.parseColor("#000000"))
            vistaRenglon.findViewById<TextView>(R.id.tvHora).text = "Hora: ${asesoria.horario}"
            vistaRenglon.findViewById<TextView>(R.id.tvHora).setTextColor(Color.parseColor("#000000"))

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonAsesoria {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.renglon_asesoria, parent, false)
        return RenglonAsesoria(vista)
    }

    override fun onBindViewHolder(holder: RenglonAsesoria, position: Int) {
        val asesoria = arrAsesorias[position]
        holder.set(asesoria)

        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return arrAsesorias.size
    }
}