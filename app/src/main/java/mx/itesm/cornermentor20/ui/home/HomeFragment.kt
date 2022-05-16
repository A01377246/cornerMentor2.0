package mx.itesm.cornermentor20.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.cornermentor20.*
import mx.itesm.cornermentor20.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), ListenerRecycler {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var userName: String

    private lateinit var baseDatos: FirebaseDatabase

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adaptadorMateria: AdaptadorMateria

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recuperar los string de submaterias para cada materia y asignarle una variable
        val arrSubMateriasMatematicas = resources.getStringArray(R.array.SubmateriasMatematicas)
        val arrSubMateriasComputacion = resources.getStringArray(R.array.SubmateriasComputacion)
        val arrSubMateriasIngenieriaDeSoftware = resources.getStringArray(R.array.SubmateriasIngenieriaDeSoftware)

        // Crear un arreglo de Materias pasando como parametro nombre (imagen opcional) y lista de submaterias)

        val arrMaterias = arrayOf(
            Materia("Computación", 0, arrSubMateriasComputacion),
            Materia("Matemáticas", 0, arrSubMateriasMatematicas),
            Materia("Ingeniería de Software", 0, arrSubMateriasIngenieriaDeSoftware)
        )
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvMaterias.layoutManager = layout
        adaptadorMateria = AdaptadorMateria(requireContext(), arrMaterias)
        binding.rvMaterias.adapter = adaptadorMateria
        adaptadorMateria.listener = this // convertir esta clase en el listener del adaptador para que al dar click se ejecute una opción
        baseDatos = Firebase.database


        /* val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        */


/*/Asesoria

        val userAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1,
            arrMaterias1
        )
        binding.svFiltrado.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svFiltrado.clearFocus()
                if(arrMaterias.toString().contains(query.toString())){
                    userAdapter.filter.filter(query)
                }
                    return false
            }


            override fun onQueryTextChange(p0: String?): Boolean {
                userAdapter.filter.filter(p0)
                return false

            }
        })

 */
        escribirDatosAsesoriaPrueba()

        return root

    }


    private fun escribirDatosAsesoriaPrueba(){ //funcion de prueba para crear algunos registros en la base de datos

        var asesoria1 = Asesoria("Inglés", "Martín", "5/3/2022", "12" ) //Modficar para que cada asesoria tenga su ruta
        var asesoria2 = Asesoria("Inglés", "Giglia", "5/3/2022", "16" )

        val referencia = baseDatos.getReference("Asesorias").push()

        referencia.setValue(asesoria1)
        referencia.setValue(asesoria2)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemClicked(position: Int) {
        val materia = adaptadorMateria.arrMaterias[position]
        println("Click en ${materia.nombre}")

        //Navegar hacia el fragmento donde se mostrarán las asesorías disponibles para deterrminada materia

        val accion = HomeFragmentDirections.actionNavigationHomeToInfoMateriaFrag(materia)

        findNavController().navigate(accion) //navegar hacia el fragmento que mostrará la materia
    }
}