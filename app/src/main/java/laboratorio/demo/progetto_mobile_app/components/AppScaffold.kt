package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.Composable


// AppScaffold decide il colore della TopBar, il colore
// del contenitore, l'elevazione, ecc.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold (
//    title: String,
//    showBackButton: Boolean = true,
//    onBackClick: () -> Unit = {},
//    topBarColor: Color = MaterialTheme.colorScheme.primary,
    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,

    topBarTitle: String = "",
    bottomBarText: String = "",

    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},

    //topBarColor: Color = MaterialTheme.colorScheme.primary,

    // Colore separato per la BottomBar
    bottomBarColor: Color = MaterialTheme.colorScheme.primary,

    // Configurazione della TopBar
    appBarConfig: AppBarConfig = AppBarConfig(
        backgroundColor = MaterialTheme.colorScheme.primary
    ),

    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold (

        topBar = {
            if(showTopBar) {

                AppTopBar(
                    title = topBarTitle,
                    showBackButton = showBackButton,
                    onBackClick = onBackClick,
                    colors = TopAppBarDefaults.topAppBarColors(

                        //containerColor = topBarColor,
                        containerColor = appBarConfig.backgroundColor,

                        //titleContentColor = Color.White,
                        titleContentColor = appBarConfig.titleColor,

                        //navigationIconContentColor = Color.White
                        navigationIconContentColor = appBarConfig.iconColor
                    )
                    //showBackButton = currentRoute != Routes.Home,
                    /*onBackClick = {
                        navController.popBackStack()
                    }*/
                )

            }

        },

        bottomBar = {

            if(showBottomBar) {

                BottomAppBar (
                    containerColor = bottomBarColor
                ) {
                    Box(

                        modifier = Modifier.fillMaxWidth(),

                        contentAlignment = Alignment.Center

                    ) {

                        Text(
                            text = bottomBarText,

                            color = Color.White
                        )

                    }

                }
            }

        }
    ) { innerPadding ->

        content(innerPadding)

    }
}