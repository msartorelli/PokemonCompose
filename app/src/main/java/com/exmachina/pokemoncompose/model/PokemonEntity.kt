package com.exmachina.pokemoncompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pokemons")
data class PokemonEntity (
    @PrimaryKey
    val name : String,
    val url: String
)

data class PokemonApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonEntity>

)