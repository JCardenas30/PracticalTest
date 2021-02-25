package com.sunwise.practicaltest.domain.repository

import com.sunwise.practicaltest.data.api.PokemonApi
import com.sunwise.practicaltest.data.db.dao.PokemonDao
import com.sunwise.practicaltest.data.entities.PokemonResponseEntity
import com.sunwise.practicaltest.data.mappers.PokemonMapper
import com.sunwise.practicaltest.domain.models.Pokemon
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking

class PokemonRepository(): BaseRepository() {
    private val mapper: PokemonMapper by lazy { PokemonMapper() }
    private val api: PokemonApi by lazy { PokemonApi() }
    private val dao: PokemonDao by lazy { database.pokemonDao() }

    fun getPokemon(result: (list: List<Pokemon>) -> Unit){
        runBlocking {
            if(existLocalPokemon())
                result(getLocalPokemon())
            else {
                getRemotePokemon { res ->
                    runBlocking {
                        saveCache(res)
                        result(res)
                    }
                }
            }
        }
    }

    private fun getRemotePokemon(result: (list: List<Pokemon>) -> Unit){
        api.pokemon()
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.computation())
                .subscribeWith(object: DisposableSingleObserver<PokemonResponseEntity>(){
                    override fun onSuccess(value: PokemonResponseEntity?) {

                        result(mapper.map(value))
                    }

                    override fun onError(e: Throwable?) {
                        result(emptyList())
                    }

                })
    }

    private suspend fun saveCache(list: List<Pokemon>){
        dao.insert(list)
    }

    private suspend fun getLocalPokemon(): List<Pokemon>{
        return dao.all()
    }

    private suspend fun existLocalPokemon(): Boolean{
        return dao.exist() > 0
    }
}