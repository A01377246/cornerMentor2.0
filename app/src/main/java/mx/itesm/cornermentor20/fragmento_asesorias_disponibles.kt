package mx.itesm.cornermentor20

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
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

    private lateinit var UIDUsuario: String

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
        adaptador.listener = this
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
        val asesoria = adaptador.arrAsesorias[position]
        val mensajeConfirmacion = AlertDialog.Builder(requireContext()) //Para preguntarle al usuario si quiere registrar la asesoria seleccionada
        mensajeConfirmacion.setTitle("Reservar Asesoría")
        mensajeConfirmacion.setIcon(androidx.appcompat.R.drawable.abc_seekbar_tick_mark_material)
        mensajeConfirmacion.setMessage("¿Estás seguro que quieres reservar una asesoría para ${args.subMateriaSeleccionada} el ${asesoria.fecha} a las ${asesoria.horario} hrs?")
        mensajeConfirmacion.setCancelable(false) // Para que el diálogo no se cierre al hacer click en otro lugar

        mensajeConfirmacion.setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface, i ->

            registrarAsesoria(asesoria) //Agregar el UID del usuario a la asesoría para reservarla
            val accion = fragmento_asesorias_disponiblesDirections.actionFragmentoAsesoriasDisponiblesToNavigationDashboard()
            findNavController().navigate(accion)


        })

        mensajeConfirmacion.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
        })

        mensajeConfirmacion.show()
        println("click en ${asesoria}")
    }

    //Esta función se ejecuta cuando el usuario acepta tomar una asesoría. Registra el UID en el registro de la asesoria seleccionada.

    private fun registrarAsesoria(asesoria: Asesoria) {
        UIDUsuario = FirebaseAuth.getInstance().currentUser!!.uid //Obtener el token único del usuario para escribirlo en el campo idUsuario de asesoría y completar el registro
        println(UIDUsuario)

        val baseDatos = FirebaseDatabase.getInstance() //Obtener instancia de la base de datos
        //Acceder a la asesoria seleccionada y registrar el ID del alumno
        val referencia = baseDatos.getReference("Asesorias/${asesoria.materia}/${asesoria.llaveAsesoria}").child("idAlumno").setValue(UIDUsuario)

    }


}