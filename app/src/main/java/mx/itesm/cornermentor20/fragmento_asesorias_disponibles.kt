package mx.itesm.cornermentor20

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import mx.itesm.cornermentor20.databinding.FragmentFragmentoAsesoriasDisponiblesBinding
import mx.itesm.cornermentor20.databinding.InfoMateriaFragmentBinding

class fragmento_asesorias_disponibles : Fragment() {

    companion object {
        fun newInstance() = fragmento_asesorias_disponibles()
    }

    private lateinit var viewModel: FragmentoAsesoriasDisponiblesVM

    private val args: fragmento_asesorias_disponiblesArgs by navArgs()

    private lateinit var binding: FragmentFragmentoAsesoriasDisponiblesBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragmentoAsesoriasDisponiblesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val subMateria = args.subMateriaSeleccionada
        binding.TVAsesorias.text = "Estas son las asesorías disponibles para: ${subMateria}"

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentoAsesoriasDisponiblesVM::class.java)
        // TODO: Use the ViewModel
    }

}