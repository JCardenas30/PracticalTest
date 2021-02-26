package com.sunwise.practicaltest.data.entities

data class PokemonResponseEntity(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonEntity>? = null
)

data class PokemonEntity(
    val name: String? = null,
    val url: String? = null
)