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
import com.kevinhe.dragonballapp.juego.OpcionesJuego
import kotlinx.coroutines.launch
import kotlin.random.Random

class DetalleFragment: Fragment() {


    private lateinit var binding: FragmentDetalleBinding
    private val viewModel: JuegoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalleBinding.inflate(inflater, container, false)
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state) {
                    is JuegoViewModel.State.PersonajeSeleccionado -> {
                        binding.tvNombre.text = state.personaje.nombre
                        binding.pbVida.progress = Random.nextInt(0, 100)
                    }
                    else -> Unit
                }

            }
        }
    }

}