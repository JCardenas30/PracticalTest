package com.sunwise.practicaltest.data.api

import com.sunwise.practicaltest.data.entities.PokemonResponseEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("api/v2/pokemon")
    fun pokemon(@Query("limit") limit: Int): Single<PokemonResponseEntity>
}