package mx.itesm.cornermentor20

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Materia(
    @SerializedName("materia")
    val nombre: String,
    @SerializedName("imagen")
    val idImagen: Int): Serializable