package mx.itesm.cornermentor20

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentoAsesoriasDisponiblesVM : ViewModel() {

    //variables observables

    val listaAsesorias = MutableLiveData<List<Asesoria>>()


    fun descargarAsesorias(subMateria: String) { // Esta función añadira a la lista de asesorías aquellas que coincidan con la selección del usuario

        val baseDatos = FirebaseDatabase.getInstance()
        val referencia = baseDatos.getReference("/Asesorias")

        referencia.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaSubMateria = mutableListOf<Asesoria>()
                for (id in snapshot.children) { //Visit each node
                    for(registro in id.children){
                        val asesoria = registro.getValue(Asesoria::class.java)
                        if(asesoria?.materia.toString() == subMateria && asesoria?.idAlumno.toString() == ""){ // Guardar submaterias que coincidan con la materia seleccionada en un arreglo y evitar aquellas que ya hayan sido registradas
                            if (asesoria != null) {
                                listaSubMateria.add(asesoria)
                    }

                        }
                    }

                }
                listaAsesorias.value = listaSubMateria


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}




