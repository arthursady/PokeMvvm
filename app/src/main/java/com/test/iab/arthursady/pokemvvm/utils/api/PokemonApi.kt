package com.test.iab.arthursady.pokemvvm.utils.api

import com.test.iab.arthursady.pokemvvm.model.Pokemon
import com.test.iab.arthursady.pokemvvm.model.PokemonForm
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.model.ResponseList
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface PokemonApi {
    @GET("pokemon/")
    fun fetchPokemons(): Call<ResponseList<PokemonMinified>>

    @GET
    fun fetchPokemonsAfter(@Url nextPage: String): Call<ResponseList<PokemonMinified>>

    @GET
    fun fetchPokemonsBefore(@Url previousPage: String): Call<ResponseList<PokemonMinified>>

    @GET("pokemon/{id}/")
    fun fetchPokemonWithId(@Path("id") id: String): Observable<Pokemon>

    @GET
    fun fetchPokemonForm(@Url url: String): Observable<PokemonForm>
}