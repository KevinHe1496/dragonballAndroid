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
import com.kevinhe.dragonballapp.databinding.ActivityLoginBinding
import com.kevinhe.dragonballapp.juego.detalles.DetalleFragment
import com.kevinhe.dragonballapp.juego.listado.ListadoFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

interface OpcionesJuego {
    fun irAlListado()
    fun irAlDetalle()
}

class JuegoActivity : AppCompatActivity(), OpcionesJuego {

    companion object {
        private val TAG_TOKEN = "token"
        fun startJuegoActivity(context: Context, token: String) {
            val intent = Intent(context, JuegoActivity::class.java)
            intent.putExtra(TAG_TOKEN, token)
            context.startActivity(intent)
        }
    }

    private val viewModel: JuegoViewModel by viewModels()
    private lateinit var biding: ActivityJuegoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        biding = ActivityJuegoBinding.inflate(layoutInflater)
        setContentView(biding.root)
        val token = intent.getStringExtra(TAG_TOKEN)
        token?.let {
            viewModel.actualizarToken(token)
        } ?: run {
            Toast.makeText(this, "No hay token. La activity se va a cerrar.", Toast.LENGTH_SHORT)
                .show()
            finish() // termina el activity
        }

        viewModel.descargarPersonajes()
        initFragments()
    }

    private fun initFragments() {
        irAlListado()
    }


    override fun irAlListado() {
        supportFragmentManager.beginTransaction().apply {
            replace(biding.flHome.id, ListadoFragment())
            addToBackStack(null)
            commit()
        }
    }

    override fun irAlDetalle() {
        supportFragmentManager.beginTransaction().apply {
            replace(biding.flHome.id, DetalleFragment())
            addToBackStack(null)
            commit()
         }

        }
    }