package mx.itesm.cornermentor20

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mx.itesm.cornermentor20.databinding.InfoMateriaFragmentBinding

class InfoMateriaFrag : Fragment() {


    companion object {
        fun newInstance() = InfoMateriaFrag()
    }

    private lateinit var viewModel: InfoMateriaVM

    private val args: InfoMateriaFragArgs by navArgs()

    private lateinit var binding: InfoMateriaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = InfoMateriaFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            val nombreMateria = args.materia.nombre
            binding.TvMateria.text = "Asesorías para ${nombreMateria}"
    }

}