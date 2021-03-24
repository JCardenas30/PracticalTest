package com.sunwise.practicaltest.domain.usecases

import com.sunwise.practicaltest.domain.models.Pokemon
import com.sunwise.practicaltest.domain.repository.PokemonRepository
import kotlinx.coroutines.runBlocking

class PokemonUseCase {
    private val repository = PokemonRepository()

    fun getPokemon(result: (list: List<Pokemon>) -> Unit){
        runBlocking {
            if(repository.existLocalPokemon())
                result(repository.getLocalPokemon())
            else {
                repository.getRemotePokemon { res ->
                    runBlocking {
                        repository.saveLocal(res)
                        result(res)
                    }
                }
            }
        }
    }

    fun remove(item: Pokemon){
        runBlocking {
            repository.remove(item)
        }
    }
}