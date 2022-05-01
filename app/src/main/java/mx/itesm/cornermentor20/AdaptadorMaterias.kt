package mx.itesm.cornermentor20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorMaterias(private val context: Context,var arrMaterias:Array<Materia>):
    RecyclerView.Adapter<AdaptadorMaterias.RenglonMaterias>(){
        class RenglonMaterias(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon){
           fun set(materias: Materia){
               vistaRenglon.findViewById<TextView>(R.id.ttvMaterias).text=materias.nombre
           }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonMaterias {
       val vista=LayoutInflater.from(context).inflate(R.layout.lista_recyclerview,
       parent,false)
        return RenglonMaterias(vista)
    }

    override fun getItemCount(): Int {
        return arrMaterias.size
    }

    override fun onBindViewHolder(holder: RenglonMaterias, position: Int) {
        val materia=arrMaterias[position]
        holder.set(materia)
    }


}
