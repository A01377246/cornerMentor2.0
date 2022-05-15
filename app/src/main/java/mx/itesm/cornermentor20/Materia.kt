package mx.itesm.cornermentor20

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Materia(
    val nombre: String,
    val idImagen: Int,
    val submaterias: Array<String>

):Serializable

    //Lista de submaterias en arreglo y pasarla a la siguiente pantalla (Arrelgo de strings)