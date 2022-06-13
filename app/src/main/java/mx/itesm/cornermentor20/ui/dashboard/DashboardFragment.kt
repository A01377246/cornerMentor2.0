package mx.itesm.cornermentor20.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.cornermentor20.AdaptadorMateria
import mx.itesm.cornermentor20.Asesoria
import mx.itesm.cornermentor20.ListenerRecycler
import mx.itesm.cornermentor20.databinding.FragmentDashboardBinding
import mx.itesm.cornermentor20.fragmento_asesorias_disponiblesDirections

class DashboardFragment : Fragment(), ListenerRecycler {


    private lateinit var adaptadorAsesoriaRegistrada: AdaptadorAsesoriaRegistradaAlumno

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        //binding?.?.setOnClickListener{
          //  val intent:Intent = Intent(requireContext(), pantallaNueva::class.java)
            //startActivity(intent)
       //}

        val arregloPruebaAsesoria = mutableListOf(Asesoria("PRUEBA", "yo", "6/13/2012","13:00-14:00"))
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvAsesoriasProgramadas.layoutManager = layout

        adaptadorAsesoriaRegistrada = AdaptadorAsesoriaRegistradaAlumno(requireContext(), arregloPruebaAsesoria)
        binding.rvAsesoriasProgramadas.adapter = adaptadorAsesoriaRegistrada
        adaptadorAsesoriaRegistrada.listener = this


        val divisor =  DividerItemDecoration(requireContext(), layout.orientation)
        binding.rvAsesoriasProgramadas.addItemDecoration(divisor)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registrarObservadores()
    }

    private fun registrarObservadores() {
        viewModel.listaAsesoriasRegistradas.observe(viewLifecycleOwner){  listaAsesoriasRegistradas ->
            val arrAsesoriasProgramadas = listaAsesoriasRegistradas.toMutableList() //convertir a Mutable List ya que se pueden quitar elementos
            adaptadorAsesoriaRegistrada.arrAsesoriasProgamadas = arrAsesoriasProgramadas
            adaptadorAsesoriaRegistrada.notifyDataSetChanged()
        }
        viewModel.descargarAsesorias()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun itemClicked(position: Int) {
        val asesoria = adaptadorAsesoriaRegistrada.arrAsesoriasProgamadas[position]
        val mensajeConfirmacion = AlertDialog.Builder(requireContext()) //Para preguntarle al usuario si quiere registrar la asesoria seleccionada
        mensajeConfirmacion.setTitle("Cancelar Asesoría")
        mensajeConfirmacion.setIcon(androidx.appcompat.R.drawable.abc_seekbar_tick_mark_material)
        mensajeConfirmacion.setMessage("¿Estás seguro que quieres cancelar esta asesoría, tendrás que reservarla de nuevo")
        mensajeConfirmacion.setCancelable(false) // Para que el diálogo no se cierre al hacer click en otro lugar

        mensajeConfirmacion.setPositiveButton("Sí", DialogInterface.OnClickListener { dialogInterface, i ->
            viewModel.liberarAsesoria(asesoria)// Quitar el UID del usuario de la asesoria para que otro usuario pueda tomarla
            adaptadorAsesoriaRegistrada.arrAsesoriasProgamadas.removeAt(position) // quitar la asesoria de las disponibles para el usuario
            adaptadorAsesoriaRegistrada.notifyDataSetChanged() //Avisar que los datos cambiaron para borrar la asesoria seleccionada de la pantalla

        })

        mensajeConfirmacion.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
        })
        mensajeConfirmacion.show()
    }
}