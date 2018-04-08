package com.test.iab.arthursady.pokemvvm.modules.pokedetails

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.test.iab.arthursady.pokemvvm.model.Pokemon
import com.test.iab.arthursady.pokemvvm.model.PokemonForm
import com.test.iab.arthursady.pokemvvm.utils.repositories.PokemonApiDetailsRepository

class PokemonDetailsViewModel: ViewModel() {
    private var repository = PokemonApiDetailsRepository()
    var pokemon: MediatorLiveData<Pokemon> = MediatorLiveData()
    var pokemonForm = MediatorLiveData<PokemonForm>()

    fun onActivityCreated(pokemonId: Int) {
        pokemon.addSource(repository.getPokemonWithId(pokemonId)) {
            pokemon.value = it
            loadPokemonForms(it)
        }
    }

    private fun loadPokemonForms(pokemon: Pokemon?) {
        val formUrl = pokemon?.forms?.firstOrNull()?.relativeUrl ?: return
        pokemonForm.addSource(repository.getPokemonForm(formUrl)) {
            pokemonForm.value = it
        }
    }
}
