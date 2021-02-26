package com.sunwise.practicaltest.domain.repository

import com.sunwise.practicaltest.data.api.PokemonApi
import com.sunwise.practicaltest.data.mappers.PokemonMapper
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class PokemonRepositoryTest {
    private var mapper: PokemonMapper? = null
    private var api: PokemonApi? = null
    private var repository: PokemonRepository? = null

    @Before
    fun init(){
        repository = PokemonRepository()
        mapper = PokemonMapper()
        api = PokemonApi()
    }

    @Test
    fun repositoryNotNull(){
        assertNotNull(repository)
    }

    @Test
    fun mapperNotNull(){
        assertNotNull(mapper)
    }

    @Test
    fun apiNotNull(){
        assertNotNull(api)
    }

    @Test
    fun getRemotePokemon() {
        repository?.getRemotePokemon {
            assertFalse("success", it.isNotEmpty())
        }
    }
}