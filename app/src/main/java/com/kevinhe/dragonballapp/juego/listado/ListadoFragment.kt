package com.kevinhe.dragonballapp.juego.listado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinhe.dragonballapp.databinding.FragmentListadoBinding
import com.kevinhe.dragonballapp.juego.JuegoViewModel
import com.kevinhe.dragonballapp.juego.PersonajeAdapter
import kotlinx.coroutines.launch
import androidx.fragment.app.activityViewModels
import com.kevinhe.dragonballapp.juego.OpcionesJuego

class ListadoFragment: Fragment() {

    private val personajesAdapter = PersonajeAdapter(
        onPersonajeClicked = { personaje ->
            viewModel.personajeSeleccionado(personaje)
        }
    )

    private val viewModel: JuegoViewModel by activityViewModels()

    private lateinit var binding: FragmentListadoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListadoBinding.inflate(inflater, container, false)
        initViews()
        initObservers()
        return binding.root
    }
    private fun initViews() {
        binding.rvPersonajes.layoutManager = LinearLayoutManager(this.context)
        binding.rvPersonajes.adapter = personajesAdapter
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state) {
                    is JuegoViewModel.State.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    is JuegoViewModel.State.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        personajesAdapter.actualizarPersonajes(state.personajes)
                     }
                    is JuegoViewModel.State.Error -> {
                        binding.pbLoading.visibility = View.GONE
                    }
                    is JuegoViewModel.State.PersonajeSeleccionado -> {
                        (activity as? OpcionesJuego)?.irAlDetalle()
                    }
                }

            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.personajeDeseleccionado()
    }
}

