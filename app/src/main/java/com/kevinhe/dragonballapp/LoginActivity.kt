package com.kevinhe.dragonballapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.kevinhe.dragonballapp.databinding.ActivityLoginBinding

// Definición de la clase LoginActivity, que hereda de AppCompatActivity
class LoginActivity : AppCompatActivity() {

    // Inicialización del ViewModel mediante delegación con 'by viewModels()'
    // Esto permite gestionar el ciclo de vida del ViewModel automáticamente
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var biding : ActivityLoginBinding

    // Método onCreate, se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Llama al método padre para la creación de la actividad
        biding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(biding.root)
        enableEdgeToEdge() // Activa el diseño de borde a borde (posiblemente para una interfaz más inmersiva)

        // Llama al método guardarUsuaruo del ViewModel
        viewModel.guardarUsuaruo(
            preferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE), // Obtiene las preferencias compartidas para almacenar datos
            usuario = "pepe", // Asigna el nombre de usuario
            password = "1234" // Asigna la contraseña
        )

        // Muestra un mensaje corto en pantalla al abrir la aplicación
        Toast.makeText(
            this, // Contexto actual (normalmente la actividad actual)
            "App abierta correctamente", // Mensaje que se mostrará al usuario
            Toast.LENGTH_SHORT // Duración del mensaje (corto, alrededor de 2 segundos)
        ).show(); // Muestra el Toast en pantalla
    }
}