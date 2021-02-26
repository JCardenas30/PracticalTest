package com.sunwise.practicaltest.view.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunwise.practicaltest.domain.models.Pokemon
import com.sunwise.practicaltest.domain.usecases.PokemonUseCase
import com.sunwise.practicaltest.domain.usecases.UserUseCase

class MainViewModel: ViewModel() {
    private val pokemonUseCase: PokemonUseCase by lazy { PokemonUseCase() }
    private val userUseCase: UserUseCase by lazy { UserUseCase() }

    val loadingLiveData = MutableLiveData(true)
    val errorLiveData = MutableLiveData<String?>()

    fun getPokemon(result: (list: List<Pokemon>) -> Unit) = pokemonUseCase.getPokemon(result)
    fun logout(ok: () -> Unit) = userUseCase.logout(ok)
}