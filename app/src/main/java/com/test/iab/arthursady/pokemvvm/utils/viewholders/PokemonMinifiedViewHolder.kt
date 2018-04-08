package com.test.iab.arthursady.pokemvvm.utils.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.test.iab.arthursady.pokemvvm.R
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.utils.listeners.PokemonListListener


class PokemonMinifiedViewHolder (private val view: View): RecyclerView.ViewHolder(view) {

    var listener: PokemonListListener? = null
        set(value) {
            field = value
            view.setOnClickListener {
                pokemon?.let {
                    listener?.onPokemonItemClicked(it)
                }
            }
        }

    var pokemon: PokemonMinified? = null
        set(value) {
            field = value
            view.findViewById<TextView>(R.id.pokemonNameTextView).text = pokemon?.name?.capitalize()
//            view.pokemonNumberTextView.text = pokemon?.id.toString()
//            view.pokemonTypeTextView.text = pokemon?.types?.joinToString(separator = "/", transform = { it.name ?: "" })
        }
}