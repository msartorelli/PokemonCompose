package com.exmachina.pokemoncompose.service

import com.exmachina.pokemoncompose.model.PokemonApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    fun listaPokemons() : Call<PokemonApiResult>
}