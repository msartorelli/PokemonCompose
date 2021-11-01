package com.exmachina.pokemoncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exmachina.pokemoncompose.ui.theme.PokemonComposeTheme
import com.exmachina.pokemoncompose.viewModel.PokemonViewModel
import com.exmachina.roomocean.model.AppDatabase

class MainActivity : ComponentActivity() {
    private val pokemonViewModel by viewModels<PokemonViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonViewModel.createDabase(AppDatabase.getDatabase(application))
        setContent {
            PokemonComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonScreen(pokemonViewModel)
                }
            }
        }
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