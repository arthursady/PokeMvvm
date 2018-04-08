package com.test.iab.arthursady.pokemvvm.utils.listeners

import com.test.iab.arthursady.pokemvvm.model.PokemonMinified


interface PokemonListListener {
    fun onPokemonItemClicked(pokemon: PokemonMinified)
}