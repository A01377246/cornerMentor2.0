package mx.itesm.cornermentor20.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.location.GnssAntennaInfo
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.cornermentor20.Asesoria
import mx.itesm.cornermentor20.ListenerRecycler
import mx.itesm.cornermentor20.R
import mx.itesm.cornermentor20.ui.AdaptadorAsesoria

class AdaptadorAsesoriaRegistradaAlumno(private val contexto: Context, var arrAsesoriasProgamadas: MutableList<Asesoria> ) :
    RecyclerView.Adapter<AdaptadorAsesoriaRegistradaAlumno.RenglonAsesoriaRegistradaAlumno>(){

    var listener: ListenerRecycler? = null


    class RenglonAsesoriaRegistradaAlumno(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon){
        fun set(asesoriaRegistrada: Asesoria){
            vistaRenglon.setBackgroundColor(Color.parseColor("#FFFFFF"))

            vistaRenglon.findViewById<TextView>(R.id.tvNombreMateria).text = asesoriaRegistrada.materia
            vistaRenglon.findViewById<TextView>(R.id.tvNombreMateria).setTextColor(Color.parseColor("#000000"))
            vistaRenglon.findViewById<TextView>(R.id.tvFechaProgramada).text = asesoriaRegistrada.fecha
            vistaRenglon.findViewById<TextView>(R.id.tvFechaProgramada).setTextColor(Color.parseColor("#000000"))
            vistaRenglon.findViewById<TextView>(R.id.tvHorario).text = asesoriaRegistrada.horario
            vistaRenglon.findViewById<TextView>(R.id.tvHorario).setTextColor(Color.parseColor("#000000"))

        }
    }


    override fun onBindViewHolder(holder: RenglonAsesoriaRegistradaAlumno, position: Int) {
        val asesoriaRegistrada = arrAsesoriasProgamadas[position]
        holder.set(asesoriaRegistrada)
        holder.vistaRenglon.setOnClickListener {
            listener?.itemClicked(position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RenglonAsesoriaRegistradaAlumno {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.renglon_asesoriaalumno, parent, false)
        return RenglonAsesoriaRegistradaAlumno(vista)
    }

    override fun getItemCount(): Int {
        return arrAsesoriasProgamadas.size
    }


}