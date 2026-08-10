package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import laboratorio.demo.progetto_mobile_app.navigation.Routes

// AppTopBar si occupa solo del contenuto della TopBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    colors: TopAppBarColors
    //colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
    //containerColor: Color = MaterialTheme.colorScheme.primary
) {
    TopAppBar(
        title = { Text(title) },

        navigationIcon = {
            if (showBackButton) {
                BackButton( onClick = onBackClick )
            }
        },

        colors = colors

//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = containerColor,
//            titleContentColor = MaterialTheme.colorScheme.onPrimary,
//            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
//        )
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {

    MaterialTheme {

        //AppTopBar(
            //title = "Registrazione",
            //onBackClick = {}
        //)

    }

}

