package com.test.iab.arthursady.pokemvvm.utils.repositories

import android.arch.lifecycle.LiveData
import com.test.iab.arthursady.pokemvvm.model.Pokemon
import com.test.iab.arthursady.pokemvvm.model.PokemonForm


interface PokemonDetailsRepository {
    fun getPokemonWithId(id: Int): LiveData<Pokemon>
    fun getPokemonForm(url: String): LiveData<PokemonForm>
}