package com.kevinhe.dragonballapp.juego

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinhe.dragonballapp.R
import com.kevinhe.dragonballapp.databinding.ActivityJuegoBinding
import com.kevinhe.dragonballapp.juego.detalles.DetalleFragment
import com.kevinhe.dragonballapp.juego.listado.ListadoFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

// Interfaz que define las opciones del juego
interface OpcionesJuego {
    fun irAlListado()  // Método para ir al listado de personajes
    fun irAlDetalle()  // Método para ir al detalle de un personaje
}

// Clase principal de la actividad del juego
class JuegoActivity : AppCompatActivity(), OpcionesJuego {

    companion object {
        // Método estático para iniciar esta actividad desde otro lugar en la app
        fun startJuegoActivity(context: Context) {
            val intent = Intent(context, JuegoActivity::class.java)
            context.startActivity(intent)
        }
    }

    // ViewModel asociado a la actividad para manejar la lógica del juego
    private val viewModel: JuegoViewModel by viewModels()
    private lateinit var biding: ActivityJuegoBinding  // Enlace con la vista (Binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ajusta la UI para ocupar toda la pantalla

        // Inicializa el binding para acceder a los elementos de la vista
        biding = ActivityJuegoBinding.inflate(layoutInflater)
        setContentView(biding.root)

        // Llama a la función para descargar la lista de personajes desde el ViewModel
        viewModel.descargarPersonajes(getSharedPreferences("my_preferences", Context.MODE_PRIVATE))

        // Inicializa los fragmentos mostrando el listado de personajes
        initFragments()
    }

    // Función que inicia los fragmentos, mostrando primero la lista de personajes
    private fun initFragments() {
        irAlListado()
    }

    // Método de la interfaz OpcionesJuego que muestra el fragmento de listado de personajes
    override fun irAlListado() {
        supportFragmentManager.beginTransaction().apply {
            replace(biding.flHome.id, ListadoFragment()) // Reemplaza el fragmento actual con el de listado
            commit() // Confirma el cambio
        }
    }

    // Método de la interfaz OpcionesJuego que muestra el fragmento de detalles de un personaje
    override fun irAlDetalle() {
        supportFragmentManager.beginTransaction().apply {
            replace(biding.flHome.id, DetalleFragment()) // Reemplaza el fragmento actual con el de detalle
            addToBackStack(Random.nextInt().toString()) // Agrega la transacción al historial de navegación
            commit() // Confirma el cambio
        }
    }
}
