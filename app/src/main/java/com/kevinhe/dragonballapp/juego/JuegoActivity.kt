package com.kevinhe.dragonballapp.juego

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
import com.kevinhe.dragonballapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JuegoActivity : AppCompatActivity() {

    private val viewModel: JuegoViewModel by viewModels()
    private lateinit var biding: ActivityJuegoBinding
    private val personajesAdapter = PersonajeAdapter(
        onPersonajeClicked = { personaje ->
            Toast.makeText(this, personaje.nombre, Toast.LENGTH_SHORT).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        biding = ActivityJuegoBinding.inflate(layoutInflater)
        setContentView(biding.root)
        initViews()
        setObservers()

        viewModel.descargarPersonajes()

    }

    private fun initViews() {
        biding.rvPersonajes.layoutManager = LinearLayoutManager(this)
        biding.rvPersonajes.adapter = personajesAdapter
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state) {
                    is JuegoViewModel.State.Loading -> {
                        biding.pbLoading.visibility = View.VISIBLE
                    }
                    is JuegoViewModel.State.Success -> {
                        biding.pbLoading.visibility = View.GONE
                        personajesAdapter.actualizarPersonajes(state.personajes)
                    }
                    is JuegoViewModel.State.Error -> {
                        biding.pbLoading.visibility = View.GONE
                    }
                }

            }
        }
    }
}