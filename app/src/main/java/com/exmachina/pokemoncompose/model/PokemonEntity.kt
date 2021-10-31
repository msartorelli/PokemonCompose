package com.exmachina.pokemoncompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity (
    @PrimaryKey
    val id : Long?,
    val name : String,
    val imageUrl: String
)