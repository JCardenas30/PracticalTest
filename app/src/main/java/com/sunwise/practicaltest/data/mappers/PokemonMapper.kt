package com.sunwise.practicaltest.data.mappers

import com.sunwise.practicaltest.data.entities.PokemonResponseEntity
import com.sunwise.practicaltest.domain.models.Pokemon

class PokemonMapper: IMapper<PokemonResponseEntity, List<Pokemon>> {

    override fun map(from: PokemonResponseEntity?): List<Pokemon> {
        val list = mutableListOf<Pokemon>()
        from?.results?.forEach { pokemonEntity ->
            val p = Pokemon(
                name = pokemonEntity.name ?: "",
                url = pokemonEntity.url ?: ""
            )
            list.add(p)
        }
        return list
    }
}