package com.exmachina.pokemoncompose.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmachina.pokemoncompose.model.AppDatabase
import com.exmachina.pokemoncompose.model.PokemonApiResult
import com.exmachina.pokemoncompose.model.PokemonDao
import com.exmachina.pokemoncompose.model.PokemonEntity
import com.exmachina.pokemoncompose.service.PokemonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonViewModel : ViewModel() {

    // private State
    private var currentEditPokemonId by mutableStateOf(-1)

    var pokemonList = mutableStateListOf<PokemonEntity>()
        private set


    private lateinit var db: AppDatabase
    private lateinit var pokemonDao: PokemonDao


    fun createDatabase(appDatabase: AppDatabase) {

        viewModelScope.launch(Dispatchers.IO) {
            db = appDatabase
            pokemonDao = db.pokemonDao()
            Log.d(this::class.java.simpleName, "createDatabase")
        }
    }

    fun loadPokemonFromWeb(onError: () -> Unit) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: PokemonService = retrofit.create(PokemonService::class.java)

        val call = service.listaPokemons()
        call.enqueue(object : Callback<PokemonApiResult> {
            override fun onResponse(
                call: Call<PokemonApiResult>,
                response: Response<PokemonApiResult>
            ) {
                // Verifica se a chamada foi bem sucedida
                if (response.isSuccessful) {
                    response.body()?.let { pokemoApiResult ->
                        populatePokemons(pokemoApiResult)
                    }
                }
            }

            override fun onFailure(call: Call<PokemonApiResult>, t: Throwable) {
                onError()
            }
        })
    }


    fun populatePokemons(pokemonApiResult: PokemonApiResult) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonApiResult.results.forEach { pokemonEntity ->
                if (db.pokemonDao().findPokemon(pokemonEntity.name) == null) {
                    val tokens = pokemonEntity.url.split("/")
                    val urlImage = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${
                        tokens[6].padStart(3, '0')
                    }.png"
                    db.pokemonDao().create(PokemonEntity(name = pokemonEntity.name, url = urlImage))
                }
            }
            pokemonList.addAll(db.pokemonDao().findAll())
        }
    }


    //State:
    val currentEditItem: PokemonEntity?
        get() = pokemonList.find { it.id == currentEditPokemonId }

    //Event: Add Item
    fun addItem(pokemon: PokemonEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonList.add(pokemon)
            db.pokemonDao().create(pokemon)
            Log.d(this::class.java.simpleName, "addItem")
        }
    }

    //Event: remove Item
    fun removeItem(pokemon: PokemonEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonList.remove(pokemon)
            db.pokemonDao().delete(pokemon)
            Log.d(this::class.java.simpleName, "removeItem")
        }
        onEditDone()// don keep the editor open when removing items
    }


    //Event: onEditItemSelected
    fun onEditItemSelected(pokemon: PokemonEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            currentEditPokemonId = pokemon.id
            Log.d(this::class.java.simpleName, "onEditItemSelected")
        }
    }

    //Event: onEditDone
    fun onEditDone() {
        currentEditPokemonId = -1
    }

    // Event: onEditItemChange
    fun onEditItemChange(pokemon: PokemonEntity) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == pokemon.id) {
            "You can only change a item with the same is as currentEditItem"
        }
        viewModelScope.launch(Dispatchers.IO) {
            pokemonList[pokemonList.indexOf(currentEditItem)] = pokemon
            db.pokemonDao().update(pokemon = pokemon)
            Log.d(this::class.java.simpleName, "onEditItemChange")
        }

    }
}
