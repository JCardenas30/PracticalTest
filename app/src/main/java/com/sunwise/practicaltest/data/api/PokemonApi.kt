package com.sunwise.practicaltest.data.api

import com.sunwise.practicaltest.data.entities.PokemonResponseEntity
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApi {
    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PokemonApiService::class.java)

    fun pokemon(limit: Int = 100): Single<PokemonResponseEntity> {
        return api.pokemon(limit)
    }

    companion object{
        const val BASE_URL = "https://pokeapi.co/"
    }
}