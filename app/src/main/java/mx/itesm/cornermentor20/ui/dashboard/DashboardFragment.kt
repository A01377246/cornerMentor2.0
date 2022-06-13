package mx.itesm.cornermentor20.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.cornermentor20.Asesoria
import mx.itesm.cornermentor20.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //binding?.?.setOnClickListener{
          //  val intent:Intent = Intent(requireContext(), pantallaNueva::class.java)
            //startActivity(intent)
       //}

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listaAsesorias = MutableLiveData<List<Asesoria>>()


        fun descargarAsesorias(subMateria: String) { // Esta función añadira a la lista de asesorías aquellas que coincidan con la selección del usuario

            val baseDatos = FirebaseDatabase.getInstance()
            val referencia = baseDatos.getReference("/Asesorias")

            referencia.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listaSubMateria = mutableListOf<Asesoria>()
                    for (id in snapshot.children) { //Visit each node
                        for(registro in id.children){
                            val asesoria = registro.getValue(Asesoria::class.java)
                            if(asesoria?.materia.toString() == subMateria){ // Guardar submaterias que coincidad con la materia seleccionada en un arreglo
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





        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}