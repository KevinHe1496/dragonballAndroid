package com.kevinhe.dragonballapp.juego

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kevinhe.dragonballapp.R
import com.kevinhe.dragonballapp.databinding.ItemPersonajeBinding
import com.kevinhe.dragonballapp.model.Personaje

class PersonajeAdapter(
    private var onPersonajeClicked: (Personaje) -> Unit
): RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>() {

    private var personajes = listOf<Personaje>()

    fun actualizarPersonajes(personajes: List<Personaje>) {
        this.personajes = personajes
        notifyDataSetChanged()
    }

    class PersonajeViewHolder(
        private val biding: ItemPersonajeBinding,
        private var onPersonajeClicked: (Personaje) -> Unit
    ): RecyclerView.ViewHolder(biding.root) {
        fun bind(personaje: Personaje) {
            biding.tvNombre.text = personaje.nombre

            Glide
                .with(biding.root)
                .load(personaje.imagenUrl)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(biding.ivPhoto)
            biding.root.setOnClickListener {
                onPersonajeClicked(personaje)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        return PersonajeViewHolder(
           biding = ItemPersonajeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onPersonajeClicked = onPersonajeClicked
        )
    }

    override fun getItemCount(): Int {
        return personajes.size
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        holder.bind(personajes[position])
    }
}