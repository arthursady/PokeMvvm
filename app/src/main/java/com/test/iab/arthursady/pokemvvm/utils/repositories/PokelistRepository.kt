package com.test.iab.arthursady.pokemvvm.utils.repositories

import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.utils.dataclasses.Listing


interface PokelistRepository {
    fun getPokemonsListing(): Listing<PokemonMinified>
}