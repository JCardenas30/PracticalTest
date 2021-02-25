package com.sunwise.practicaltest.domain.repository

import com.sunwise.PokemonApp
import com.sunwise.practicaltest.data.db.PokemonDatabase

open class BaseRepository {
    val database: PokemonDatabase by lazy {
        PokemonDatabase.getDatabase(PokemonApp.context)
    }
}