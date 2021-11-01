package com.exmachina.pokemoncompose.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmachina.pokemoncompose.model.PokemonApiResult
import com.exmachina.pokemoncompose.model.PokemonEntity
import com.exmachina.roomocean.model.AppDatabase
import com.exmachina.roomocean.model.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    var pokemons = mutableStateListOf<PokemonEntity>()
        private set


    private lateinit var db: AppDatabase
    private lateinit var pokemonDao: PokemonDao


    fun createDatabase(appDatabase: AppDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            db = appDatabase
            pokemonDao = db.pokemonDao()

            pokemons.addAll(pokemonDao.findAll())
        }

    }

    fun showPokemon(pokemonApiResult: PokemonApiResult) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonApiResult.results.forEach { pokemonEntity ->
                if (!pokemons.contains(pokemonEntity))
                    pokemons.add(pokemonEntity)
            }
        }
    }
}
