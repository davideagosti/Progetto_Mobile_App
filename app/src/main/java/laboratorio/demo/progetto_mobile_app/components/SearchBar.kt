package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    placeholderText: String = "Cerca un luogo",
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit
) {
    // Barra ricerca
    OutlinedTextField(

        value = searchText,

        onValueChange = {newText ->
            onSearchTextChange(newText)
        },

        modifier = modifier,

        // Text Box dove fare la ricerca posto
        placeholder = {
            Text(
                text = placeholderText,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.5f
                )
            )
        },

        // Icona di ricerca a sinistra
        leadingIcon = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Cerca"
            )
        },

        // X per cancellare il testo
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onClear()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancella ricerca"
                    )
                }
            }
        },

        shape = RoundedCornerShape(50.dp),

        singleLine = true,

        // Premendo il tasto cerca della tastiera
        // viene eseguita la ricerca
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        )
    )
}