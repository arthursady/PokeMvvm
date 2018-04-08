package com.test.iab.arthursady.pokemvvm.utils.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.utils.api.PokemonApi
import java.util.concurrent.Executor


class PokemonDataSourceFactory (private val pokemonApi: PokemonApi, private val retryExecutor: Executor) : DataSource.Factory<String, PokemonMinified>() {
    val sourceLiveData = MutableLiveData<PageKeyedPokemonsDataSource>()
    override fun create(): DataSource<String, PokemonMinified> {
        val source = PageKeyedPokemonsDataSource(pokemonApi, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}