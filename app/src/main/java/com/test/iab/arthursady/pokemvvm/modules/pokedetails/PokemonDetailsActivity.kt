package com.test.iab.arthursady.pokemvvm.modules.pokedetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.bumptech.glide.Glide
import com.test.iab.arthursady.pokemvvm.R
import com.test.iab.arthursady.pokemvvm.model.Pokemon
import com.test.iab.arthursady.pokemvvm.model.PokemonForm
import com.test.iab.arthursady.pokemvvm.modules.base.BaseActivity
import com.test.iab.arthursady.pokemvvm.utils.constants.IntentConstants.POKEMON_ID
import kotlinx.android.synthetic.main.pokemon_details.*

class PokemonDetailsActivity: BaseActivity() {

    var viewModel: PokemonDetailsViewModel? = null

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_details)
        viewModel = ViewModelProviders.of(this).get(PokemonDetailsViewModel::class.java)
        registerObservers()
        val pokemonId = intent.extras.getInt(POKEMON_ID, -1)
        if (pokemonId == -1) return
        viewModel?.onActivityCreated(pokemonId)
    }

    //endregion

    //region Private

    private fun pokemonLoaded(pokemon: Pokemon?) {
        pokemon ?: return
        pokemonNameTextView.text = pokemon.name?.capitalize()
        val text = "Nº ${pokemon.id}"
        pokemonNumberTextView.text = text
    }

    private fun pokemonFormLoaded(pokemonForm: PokemonForm?) {
        pokemonForm ?: return
        Glide.with(this)
                .load(pokemonForm.sprites?.frontDefault)
                .into(pokemonImageView)
    }

    private fun registerObservers() {
        viewModel?.pokemon?.observe(this, Observer {
            pokemonLoaded(it)
        })

        viewModel?.pokemonForm?.observe(this, Observer {
            pokemonFormLoaded(it)
        })
    }

    //endregion
}
