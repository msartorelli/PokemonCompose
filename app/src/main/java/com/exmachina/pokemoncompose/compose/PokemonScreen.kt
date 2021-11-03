package com.exmachina.pokemoncompose.compose


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.exmachina.pokemoncompose.model.PokemonEntity
import com.exmachina.pokemoncompose.util.generateRandomPokemonEntity

/**
 * Stateless component that is responsible for the entire pokemon screen.
 *
 * @param items (state) list of [PokemonEntity] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 */
@Composable
fun PokemonScreen(
    items: List<PokemonEntity>,
    currentlyEditing: PokemonEntity?,
    onAddItem: (PokemonEntity) -> Unit,
    onRemoveItem: (PokemonEntity) -> Unit,
    onStartEditing: (PokemonEntity) -> Unit,
    onEditItemChange: (PokemonEntity) -> Unit,
    onEditDone: () -> Unit
) {
    val enableTopSection = currentlyEditing == null
    Column {
        // add PokemonEntityInputBackground and PokemonItem at top of PokemonScreen
        PokemonItemInputBackground(elevate = enableTopSection) {
            if (enableTopSection) {
                PokemonItemEntryInput(onItemComplete = onAddItem)
            } else {
                Text(
                    "Editing Item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }

        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) { pokemon ->
                if (currentlyEditing?.id == pokemon.id) {
                    PokemonItemInlineEditor(
                        itemPokemon = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = { onRemoveItem(pokemon) }
                    )
                } else {
                    PokemonRow(
                        pokemon = pokemon,
                        onItemClicked = { onStartEditing(it) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }

        // For quick testing, a random item generator button
        Button(
            onClick = { onAddItem(generateRandomPokemonEntity()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}

/**
 * Stateless composable that displays a full-width [PokemonEntity].
 *
 * @param pokemon item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun PokemonRow(
    pokemon: PokemonEntity,
    onItemClicked: (PokemonEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(pokemon) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(pokemon.name)
        if (pokemon.url.isNotBlank()) {
            Image(
                painter = rememberImagePainter(
                    data = pokemon.url,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )

        }
    }
}

@Composable
fun PokemonItemEntryInput(onItemComplete: (PokemonEntity) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (url, setUrl) = remember { mutableStateOf("") }
    val submit = {
        if (text.isNotBlank()) {
            onItemComplete(PokemonEntity(name = text, url = url))
            setUrl(" ")
            setText("")
        }
    }
    // onItemComplete is a event will fire when an item is  completed by the user
    PokemonItemInput(
        text = text,
        onTextChange = setText,
        url = url,
        onUrlChange = setUrl,
        submit = submit,
    ) {
        PokemonEditButton(onClick = submit, text = "Add", enabled = text.isNotBlank())
    }
}

@Composable
fun PokemonItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    url: String,
    onUrlChange: (String) -> Unit,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp)
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            PokemonInputText(
                text,
                onTextChange,
                modifier = Modifier
                    .padding(end = 8.dp),
                onImeAction = submit
            )
            PokemonInputUrl(
                url = url,
                onUrlChange = onUrlChange,
                modifier = Modifier
                    .padding(end = 8.dp),
                onImeAction = submit
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun PokemonItemInlineEditor(
    itemPokemon: PokemonEntity,
    onEditItemChange: (PokemonEntity) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) = PokemonItemInput(
    text = itemPokemon.name,
    onTextChange = { onEditItemChange(itemPokemon.copy(name = it, id = itemPokemon.id)) },
    url = itemPokemon.url,
    onUrlChange = { onEditItemChange(itemPokemon.copy(url = it, id = itemPokemon.id)) },
    submit = onEditDone,
    buttonSlot = {
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                Text(
                    text = "\uD83D\uDCBE", // Floppy disk
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
            TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                Text(
                    text = "❌", // Floppy disk
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
)


@Preview
@Composable
fun PreviewPokemonScreen() {
    val items = listOf(
        PokemonEntity(
            name = "bulbasaur",
            url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png"
        ),
        PokemonEntity(
            name = "ivysaur",
            url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/002.png"
        ),
        PokemonEntity(
            name = "venusaur",
            url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/003.png"
        ),
        PokemonEntity(
            name = "charmander",
            url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png"
        ),

        )
    PokemonScreen(items, null, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewPokemonRow() {
    val pokemon = remember { generateRandomPokemonEntity() }
    PokemonRow(pokemon = pokemon, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}

@Preview
@Composable
fun PreviewPokemonInputItem() = PokemonItemEntryInput(onItemComplete = {})