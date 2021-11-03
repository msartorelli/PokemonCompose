package com.exmachina.pokemoncompose.model

import androidx.room.*

@Dao
interface  PokemonDao {
    @Query("SELECT * FROM pokemons")
    suspend fun findAll() : List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE name = :nameVal" )
    suspend fun findPokemon(nameVal: String) : PokemonEntity?

    @Insert
    suspend fun create(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Update
    suspend fun update(pokemon: PokemonEntity)

}