package mx.itesm.cornermentor20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import mx.itesm.cornermentor20.R
import mx.itesm.cornermentor20.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding // Obtener accesso a las objetos de la interfaz de login screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root) // establecer binding como la vista

        binding.botonIniciarConGoogle.setOnClickListener{
            autenticarConGoogle()
        }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){ res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if(result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser // who logged in?
            println("Welcome: ${user?.displayName}") //print username
            println("Correo: ${user?.email}")
            println("Token: ${user?.uid}")
            //if everything is correct, enter app using an intent, load something else

            Toast.makeText(this, "Bienvenido a CornerMentor, ${user?.displayName}", Toast.LENGTH_SHORT)


            val appint = Intent(this, MainActivity::class.java) // requires content and main activity :: represent metadata
            startActivity(appint)
            finish()

        }else{
            print("Authentication error, try again")
        }

    }

    private fun autenticarConGoogle() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

}