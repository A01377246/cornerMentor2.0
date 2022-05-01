package mx.itesm.cornermentor20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorMateria(private val context: Context, var arrMaterias:Array<Materia>):
    RecyclerView.Adapter<AdaptadorMateria.RenglonMateria>(){
        class RenglonMateria(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon){
           fun set(materia: Materia){
               vistaRenglon.findViewById<TextView>(R.id.tvMateria).text = materia.nombre
               vistaRenglon.findViewById<ImageView>(R.id.imgMateria).setImageResource(R.drawable.notebook_and_pen) //Poner una imagen de un cuaderno y pluma por el momento, reemplazar
                                                                                                                    //Después con algo alusivo a la materia
           }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonMateria {
        //Cuando un renglón es requerido
       val vista=LayoutInflater.from(context).inflate(R.layout.renglon_materia,
       parent,false)
        return RenglonMateria(vista)
    }

    override fun getItemCount(): Int {
        //Regresa el número de renglones de la lista
        return arrMaterias.size
    }

    override fun onBindViewHolder(holder: RenglonMateria, position: Int) {
        //llenar con valores un renglón (position)
        val materia= arrMaterias[position]
        holder.set(materia)
    }


}
