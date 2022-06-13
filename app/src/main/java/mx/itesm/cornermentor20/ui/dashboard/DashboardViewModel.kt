package mx.itesm.cornermentor20.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.cornermentor20.Asesoria

class DashboardViewModel : ViewModel() {
    //variables observables

    val listaAsesoriasRegistradas = MutableLiveData<List<Asesoria>>()
    var UIDUsuario = FirebaseAuth.getInstance().currentUser?.uid

    fun descargarAsesorias() { // Esta función añadira a la lista de asesorías aquellas que tengan registrado el ID del usuario

        val baseDatos = FirebaseDatabase.getInstance()
        val referencia = baseDatos.getReference("/Asesorias")

        referencia.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaAsesorias = mutableListOf<Asesoria>()
                for (id in snapshot.children) { //Visitar cada materia
                    for(registro in id.children){ //visitar cada asesoria de la materia
                        val asesoria = registro.getValue(Asesoria::class.java)
                        if(asesoria?.idAlumno.toString() == UIDUsuario){ // Guardar asesorias que han sido registradas con el ID del usuario
                            if (asesoria != null) {
                                listaAsesorias.add(asesoria) //agregar a la lista aquellas asesorías que "Pertenezcan al usuario"
                            }

                        }
                    }

                }
                listaAsesoriasRegistradas.value = listaAsesorias


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }
    fun liberarAsesoria(asesoriaACancelar: Asesoria){
        val baseDatos = FirebaseDatabase.getInstance()
        //Borrar el id del alumno para que la asesoría sea liberada y vuelva a ser mostrada en asesorías disponibles
        val referencia = baseDatos.getReference("/Asesorias/${asesoriaACancelar.materia}/${asesoriaACancelar.llaveAsesoria}").
        child("idAlumno").setValue("")

    }


}