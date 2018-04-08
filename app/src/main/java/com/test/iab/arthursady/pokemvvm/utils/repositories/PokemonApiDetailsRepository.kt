package com.test.iab.arthursady.pokemvvm.utils.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.test.iab.arthursady.pokemvvm.model.Pokemon
import com.test.iab.arthursady.pokemvvm.model.PokemonForm
import com.test.iab.arthursady.pokemvvm.utils.ApiConstants
import com.test.iab.arthursady.pokemvvm.utils.api.PokemonApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class PokemonApiDetailsRepository: PokemonDetailsRepository {
    private var pokemonApi: PokemonApi

    override fun getPokemonWithId(id: Int): LiveData<Pokemon> {
        val liveData = MutableLiveData<Pokemon>()
        pokemonApi.fetchPokemonWithId(id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    liveData.value = it
                }, {
                    Log.e("RetrofitError", it.localizedMessage, it)
                })
        return liveData
    }

    override fun getPokemonForm(url: String): LiveData<PokemonForm> {
        val liveData = MutableLiveData<PokemonForm>()
        pokemonApi.fetchPokemonForm(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    liveData.value = it
                }, {
                    Log.e("RetrofitError", it.localizedMessage, it)
                })
        return liveData
    }

    init {
        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("API", it)
        })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(okHttpClient)
                .build()

        pokemonApi = retrofit.create(PokemonApi::class.java)
    }

}