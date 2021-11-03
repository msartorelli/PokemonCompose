package com.exmachina.pokemoncompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val url: String
)

data class PokemonApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonEntity>

)
