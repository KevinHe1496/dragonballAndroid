package com.kevinhe.dragonballapp.juego

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kevinhe.dragonballapp.R
import com.kevinhe.dragonballapp.databinding.ActivityLoginBinding

class JuegoActivity : AppCompatActivity() {

    private lateinit var biding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(biding.root)
    }
}