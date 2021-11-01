package com.exmachina.pokemoncompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exmachina.pokemoncompose.model.PokemonApiResult
import com.exmachina.pokemoncompose.service.PokemonService
import com.exmachina.pokemoncompose.ui.theme.PokemonComposeTheme
import com.exmachina.pokemoncompose.viewModel.PokemonViewModel
import com.exmachina.roomocean.model.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val pokemonViewModel by viewModels<PokemonViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonScreen(pokemonViewModel)
                }
            }
        }
        pokemonViewModel.createDatabase(AppDatabase.getDatabase(application))

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
                        pokemonViewModel.showPokemon(pokemoApiResult)
                    }
                }
            }

            override fun onFailure(call: Call<PokemonApiResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error loading Pokemon Data", Toast.LENGTH_LONG)
                    .show()
            }
        })

    }
}

@Composable
fun PokemonScreen(pokemonViewModel: PokemonViewModel) {
    Text(text = "Qtde ${pokemonViewModel.pokemons.size}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonComposeTheme {
            PokemonScreen(PokemonViewModel())
    }
}