package com.kevinhe.dragonballapp.juego.detalles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kevinhe.dragonballapp.databinding.FragmentDetalleBinding
import com.kevinhe.dragonballapp.juego.JuegoViewModel
import com.kevinhe.dragonballapp.model.Personaje
import kotlinx.coroutines.launch
import kotlin.random.Random

// Fragmento que muestra los detalles de un personaje seleccionado
class DetalleFragment: Fragment() {

    private lateinit var binding: FragmentDetalleBinding // Binding para acceder a la vista
    private val viewModel: JuegoViewModel by activityViewModels() // ViewModel compartido con la actividad

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla la vista del fragmento y la enlaza con el binding
        binding = FragmentDetalleBinding.inflate(inflater, container, false)

        // Inicia la observación de cambios en el ViewModel
        initObservers()
        return binding.root
    }

    private fun initViews(personaje: Personaje) {
        with(binding) {
        // Muestra el nombre del personaje seleccionado en un TextView
        tvNombre.text = personaje.nombre

            // Asigna un valor a la barra de vida (entre 0 y 100)
            pbVida.progress = personaje.vidaActual
        bGolpear.setOnClickListener {
            viewModel.golpearPersonaje(personaje)
            // Asigna un valor a la barra de vida (entre 0 y 100)
            pbVida.progress = personaje.vidaActual
            }
        }
    }

    // Función que observa el estado de la UI en el ViewModel y actualiza la interfaz cuando cambia
    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state -> // Recolecta cambios en el estado del ViewModel
                when(state) {
                    is JuegoViewModel.State.PersonajeSeleccionado -> {
                        initViews(state.personaje)

                    }
                    else -> Unit // Si el estado no es relevante, no hace nada
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()
        viewModel.personajeDeseleccionado()
    }

}
