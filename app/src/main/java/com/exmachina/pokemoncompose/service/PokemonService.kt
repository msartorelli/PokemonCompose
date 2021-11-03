package com.exmachina.pokemoncompose.service

import com.exmachina.pokemoncompose.model.PokemonApiResult
import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {
    @GET("pokemon")
    fun listaPokemons() : Call<PokemonApiResult>
}