package mx.itesm.cornermentor20

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mx.itesm.cornermentor20.databinding.InfoMateriaFragmentBinding
import java.text.FieldPosition

class InfoMateriaFrag : ListFragment() {


    companion object {
        fun newInstance() = InfoMateriaFrag()
    }

    private lateinit var viewModel: InfoMateriaVM

    private val args: InfoMateriaFragArgs by navArgs()

    private lateinit var binding: InfoMateriaFragmentBinding

    private lateinit var arrAsesorias: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = InfoMateriaFragmentBinding.inflate(layoutInflater)
        return binding.root

        binding.btnSiguiente.setOnClickListener {
            val action = InfoMateriaFragDirections.actionInfoMateriaFrag2ToPantallaNueva(tipoProfesor = "Nombres")
            findNavController().navigate(action)
        }
        //

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            val nombreMateria = args.materia.nombre
            binding.TvMateria.text = "Asesorías para ${nombreMateria}"
            println("La lista de submaterias para ${args.materia.nombre} es ${args.materia.submaterias[5]}")

            val adaptadorsubMaterias=ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item,args.materia.submaterias)





            
    }



}