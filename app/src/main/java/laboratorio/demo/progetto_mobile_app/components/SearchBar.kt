package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    // Barra ricerca
    OutlinedTextField(

        value = searchText,

        onValueChange = {newText ->
            //searchText = it
            onSearchTextChange(newText)
        },

        modifier = modifier,

        placeholder = {
            Text(
                "Cerca luogo o destinazione..."
            )
        },


        leadingIcon = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Cerca"
            )

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