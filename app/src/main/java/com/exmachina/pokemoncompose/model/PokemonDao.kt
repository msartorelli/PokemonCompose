package com.exmachina.roomocean.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.exmachina.pokemoncompose.model.PokemonEntity
import java.util.*

@Dao
interface  PokemonDao {
    @Query("SELECT * FROM pokemons")
    fun findAll() : List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE name = :nameVal" )
    fun findPokemon(nameVal: String) : PokemonEntity

    @Insert
    fun create(pokemon: PokemonEntity)

}