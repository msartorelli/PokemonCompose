package com.exmachina.pokemoncompose.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun createDabase(appDatabase: AppDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            db = appDatabase
            pokemonDao = db.pokemonDao()

            pokemons.addAll(pokemonDao.findAll())

            var pokemonEntity = PokemonEntity(null, "Joao", "Http://marco.com.br/image1.jpg")
            pokemonDao.create(pokemonEntity)
            pokemonEntity = PokemonEntity(null, "maria", "Http://marco.com.br/image2.jpg")
            pokemonDao.create(pokemonEntity)
            pokemonEntity = PokemonEntity(null, "Pedro", "Http://marco.com.br/image3.jpg")
            pokemonDao.create(pokemonEntity)
        }

    }


}