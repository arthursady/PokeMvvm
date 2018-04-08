package com.test.iab.arthursady.pokemvvm.modules.pokelist

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.modules.pokedetails.PokemonDetailsActivity
import com.test.iab.arthursady.pokemvvm.utils.constants.IntentConstants.POKEMON_ID
import com.test.iab.arthursady.pokemvvm.utils.dataclasses.Listing
import com.test.iab.arthursady.pokemvvm.utils.dataclasses.NetworkState
import com.test.iab.arthursady.pokemvvm.utils.repositories.PokelistApiRepository
import com.test.iab.arthursady.pokemvvm.utils.repositories.PokelistRepository
import java.util.concurrent.Executors

class PokemonListViewModel: ViewModel() {

    var repository: PokelistRepository = PokelistApiRepository(Executors.newFixedThreadPool(5))
    var listing: Listing<PokemonMinified>? = null
    var repoResult: MediatorLiveData<PagedList<PokemonMinified>> = MediatorLiveData()
    var networkState: MediatorLiveData<NetworkState> = MediatorLiveData()
    var refreshState: MediatorLiveData<NetworkState> = MediatorLiveData()

    fun refresh() {
        listing?.refresh?.invoke()
    }

    fun retry() {
        listing?.retry?.invoke()
    }

    fun onActivityCreated() {
        listing = repository.getPokemonsListing()
        listing?.let {listing ->
            networkState.addSource(listing.networkState) {
                networkState.value = it
            }

            refreshState.addSource(listing.refreshState) {
                refreshState.value = it
            }

            repoResult.addSource(listing.pagedList) {
                repoResult.value = it
            }
        }
    }

    fun onPokemonItemClicked(context: Context, pokemon: PokemonMinified) {
        val intent = Intent(context, PokemonDetailsActivity::class.java)
        intent.putExtra(POKEMON_ID, pokemon.id)
        context.startActivity(intent)
    }
}
