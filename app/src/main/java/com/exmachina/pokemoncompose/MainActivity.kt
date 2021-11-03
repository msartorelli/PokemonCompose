package com.exmachina.pokemoncompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exmachina.pokemoncompose.compose.PokemonScreen
import com.exmachina.pokemoncompose.model.AppDatabase
import com.exmachina.pokemoncompose.ui.theme.PokemonComposeTheme
import com.exmachina.pokemoncompose.viewModel.PokemonViewModel

class MainActivity : ComponentActivity() {
    private val pokemonViewModel by viewModels<PokemonViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonActivityScreen(pokemonViewModel)
                }
            }
        }
        pokemonViewModel.createDatabase(AppDatabase.getDatabase(application))
        pokemonViewModel.loadPokemonFromWeb {
            Toast.makeText(
                applicationContext,
                "Error loading Pokemon Data",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }
}

@Composable
fun PokemonActivityScreen(pokemonViewModel: PokemonViewModel) {
    PokemonScreen(
        items = pokemonViewModel.pokemonList,
        currentlyEditing = pokemonViewModel.currentEditItem,
        onAddItem = pokemonViewModel::addItem,
        onRemoveItem = pokemonViewModel::removeItem,
        onStartEditing = pokemonViewModel::onEditItemSelected,
        onEditItemChange = pokemonViewModel::onEditItemChange,
        onEditDone = pokemonViewModel::onEditDone
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonComposeTheme {
        PokemonActivityScreen(PokemonViewModel())
    }
}