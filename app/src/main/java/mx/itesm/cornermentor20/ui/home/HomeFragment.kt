package mx.itesm.cornermentor20.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mx.itesm.cornermentor20.AdaptadorMateria
import mx.itesm.cornermentor20.Materia
import mx.itesm.cornermentor20.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var userName: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var adaptadorMateria: AdaptadorMateria? = null

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val arrMaterias = arrayOf(Materia("Cambio climático y sostenibilidad", 0), Materia("Inglés", 0), Materia("Cálculo 1" , 0))
        val layout = LinearLayoutManager(requireContext())
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.rvMaterias.layoutManager = layout
        adaptadorMateria = AdaptadorMateria(requireContext(), arrMaterias)
        binding.rvMaterias.adapter = adaptadorMateria

       /* val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        */
        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}