package com.test.iab.arthursady.pokemvvm.utils.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.model.ResponseList
import com.test.iab.arthursady.pokemvvm.utils.api.PokemonApi
import com.test.iab.arthursady.pokemvvm.utils.dataclasses.NetworkState
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor


class PageKeyedPokemonsDataSource(private val pokemonApi: PokemonApi, private val retryExecutor: Executor): PageKeyedDataSource<String, PokemonMinified>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
            params: PageKeyedDataSource.LoadParams<String>,
            callback: PageKeyedDataSource.LoadCallback<String, PokemonMinified>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<String>, callback: PageKeyedDataSource.LoadCallback<String, PokemonMinified>) {
        networkState.postValue(NetworkState.LOADING)
        pokemonApi.fetchPokemonsAfter(params.key).enqueue(
                object: retrofit2.Callback<ResponseList<PokemonMinified>> {
                    override fun onFailure(call: Call<ResponseList<PokemonMinified>>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                    }

                    override fun onResponse(
                            call: Call<ResponseList<PokemonMinified>>,
                            response: Response<ResponseList<PokemonMinified>>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            val items = data?.results ?: emptyList()
                            val next = data?.next
                            retry = null
                            callback.onResult(items, next)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            retry = {
                                loadAfter(params, callback)
                            }
                            networkState.postValue(
                                    NetworkState.error("error code: ${response.code()}"))
                        }
                    }
                }
        )

    }

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<String>, callback: PageKeyedDataSource.LoadInitialCallback<String, PokemonMinified>) {

        val request = pokemonApi.fetchPokemons()

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val data = response.body()
            val items = data?.results ?: emptyList()
            val next = data?.next
            val previous = data?.previous
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items, previous, next)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }
}