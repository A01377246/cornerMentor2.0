package mx.itesm.cornermentor20

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mx.itesm.cornermentor20.databinding.FragmentFragmentoAsesoriasDisponiblesBinding
import mx.itesm.cornermentor20.databinding.InfoMateriaFragmentBinding
import mx.itesm.cornermentor20.ui.AdaptadorAsesoria

class fragmento_asesorias_disponibles : Fragment(), ListenerRecycler{

    private lateinit var adaptador: AdaptadorAsesoria

    companion object {
        fun newInstance() = fragmento_asesorias_disponibles()
    }

    private val viewModel: FragmentoAsesoriasDisponiblesVM by viewModels()

    private val args: fragmento_asesorias_disponiblesArgs by navArgs()

    private lateinit var binding: FragmentFragmentoAsesoriasDisponiblesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragmentoAsesoriasDisponiblesBinding.inflate(layoutInflater)

        configurarRecyclerView()

        return binding.root
    }

    private fun configurarRecyclerView() {
        val arrAsesoria = arrayOf(Asesoria("Prueba", "Griselda", "5/16/2022", "18:00", ""))
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvAsesorias.layoutManager = layout

        val divisor = DividerItemDecoration(requireContext(), layout.orientation)
        binding.rvAsesorias.addItemDecoration(divisor)

        adaptador = AdaptadorAsesoria(requireContext(), arrAsesoria)
        binding.rvAsesorias.adapter = adaptador
        adaptador.listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val subMateria = args.subMateriaSeleccionada
        binding.TVAsesorias.text = "Estas son las asesorías disponibles para: ${subMateria}"
        registrarObservadores()

    }

    private fun registrarObservadores() {
        viewModel.listaAsesorias.observe(viewLifecycleOwner){ listaAsesorias ->
            val arregloAsesorias = listaAsesorias.toTypedArray()
            adaptador.arrAsesorias = arregloAsesorias
            adaptador.notifyDataSetChanged()

        }
        viewModel.descargarAsesorias(args.subMateriaSeleccionada)
    }

    override fun itemClicked(position: Int) {
        val idAsesoria = adaptador.arrAsesorias[position]
        println("click en ${idAsesoria}")
    }


}