package laboratorio.demo.progetto_mobile_app.screens

import laboratorio.demo.progetto_mobile_app.R

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ExitToApp

import androidx.compose.material3.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import android.app.Activity
import androidx.compose.material3.MaterialTheme
import laboratorio.demo.progetto_mobile_app.components.AccountMenu
import laboratorio.demo.progetto_mobile_app.components.SearchBar
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
    ) {

    var searchText by remember {
        mutableStateOf("")
    }

    var menuOpen by remember {
        mutableStateOf(false)
    }

    // Controlla la visualizzazione della finestra di conferma
    var showExitDialog by remember {
        mutableStateOf(false)
    }

    // Recupera l'Activity corrente
    val activity = LocalContext.current as? Activity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // Barra superiore Home
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Barra ricerca
                SearchBar(
                    modifier = Modifier.weight(1f)
                )
//                OutlinedTextField(
//                    value = searchText,
//                    onValueChange = {
//                        searchText = it
//                    },
//
//                    modifier = Modifier
//                        .weight(1f),
//                    placeholder = {
//                        Text(
//                            "Cerca luogo o destinazione..."
//                        )
//                    },
//
//                    leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Cerca"
//                        )
//                    },
//
//                    shape = RoundedCornerShape(50.dp),
//                    singleLine = true
//                )

                // Spazio tra barra ricerca e icona account
                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                AccountMenu(navController)
                //AccountMenu(navController)

                // Contenitore dell'icona account e del suo menu
//                Box {
//
//                    // Icona account
//                    IconButton(
//                        onClick = {
//                            // Apre il menu account
//                            menuOpen = true
//                        }
//                    ) {
//
//                        Image(
//                            painter = painterResource(id = R.drawable.avatar_account_guest),
//                            contentDescription = "Account",
//                            modifier = Modifier
//                                .size(48.dp)
//                                .shadow(
//                                    elevation = 4.dp,
//                                    shape = CircleShape
//                                )
//                                .clip(CircleShape)
//                                .border(
//                                    2.dp,
//                                    Color.LightGray,
//                                    CircleShape
//                                )
//                        )
////                        Icon(
////                            imageVector = Icons.Default.AccountCircle,
////                            contentDescription = "Account",
////                            modifier = Modifier.size(45.dp)
////                        )
//                    }
//
//
//                    // Il menu viene creato solo quando viene aperto
//                    // Evita rallentamenti all'avvio dell'app
//                    if (menuOpen) {
//
//                        DropdownMenu(
//
//                            // Il menu è già visibile perché creato dentro l'if
//                            expanded = true,
//
//                            // Chiusura del menu cliccando fuori
//                            onDismissRequest = {
//                                menuOpen = false
//                            },
//
//                            // Dimensione del riquadro menu
//                            modifier = Modifier.width(220.dp)
//                        ) {
//
//                            // ===========================
//                            // SEZIONE ACCESSO ACCOUNT
//                            // ===========================
//
//                            Text(
//                                text = "Account",
//                                fontSize = 13.sp,
//                                color = Color.Gray,
//                                modifier = Modifier.padding(
//                                    start = 16.dp,
//                                    top = 8.dp,
//                                    bottom = 4.dp
//                                )
//                            )
//
//                            // Opzione registrazione nuovo utente
//                            DropdownMenuItem(
//
//                                leadingIcon = {
//                                  Icon(
//                                      imageVector = Icons.Default.PersonAdd,
//                                      contentDescription = null
//                                  )
//                                },
//
//                                text = {
//                                    Text("Registrati")
//                                },
//
//                                onClick = {
//                                    menuOpen = false
//                                    // TODO: apertura pagina registrazione
//                                }
//                            )
//
//
//                            // Opzione accesso utente esistente
//                            DropdownMenuItem(
//
//                                leadingIcon = {
//                                    Icon(
//                                        imageVector = Icons.Default.Login,
//                                        contentDescription = null
//                                    )
//                                },
//
//                                text = {
//                                    Text("Accedi")
//                                },
//
//                                onClick = {
//                                    menuOpen = false
//                                    // TODO: apertura pagina login
//                                }
//                            )
//
//
//                            // Separatore tra accesso e gestione account
//                            Divider(
//                                thickness = 2.dp,
//                                color = Color.LightGray,
//                                modifier = Modifier.padding(
//                                    vertical = 8.dp
//                                )
//                            )
//
//                            // ===========================
//                            // SEZIONE GESTIONE ACCOUNT
//                            // ===========================
//
//                            Text(
//                                text = "Gestione account",
//                                fontSize = 13.sp,
//                                color = Color.Gray,
//                                modifier = Modifier.padding(
//                                    start = 16.dp,
//                                    top = 4.dp,
//                                    bottom = 4.dp
//                                )
//                            )
//
//
//                            // Modifica dati account
//                            DropdownMenuItem(
//
//                                leadingIcon = {
//                                    Icon(
//                                        imageVector = Icons.Default.ManageAccounts,
//                                        contentDescription = null
//                                    )
//                                },
//
//                                text = {
//                                    Text("Modifica account")
//                                },
//
//                                onClick = {
//                                    menuOpen = false
//                                    // TODO: modifica nome/password
//                                }
//                            )
//
//
//                            // Disconnessione account
//                            DropdownMenuItem(
//
//                                leadingIcon = {
//                                    Icon(
//                                        imageVector = Icons.Default.Logout,
//                                        contentDescription = null
//                                    )
//                                },
//
//                                text = {
//                                    Text("Disconnetti")
//                                },
//
//                                onClick = {
//                                    menuOpen = false
//                                    // TODO: logout account
//                                }
//                            )
//
//                            Divider(
//                                thickness = 2.dp,
//                                color = Color.LightGray,
//                                modifier = Modifier.padding(
//                                    vertical = 8.dp
//                                )
//                            )
//
//                            // ===========================
//                            // SEZIONE APPLICAZIONE
//                            // ===========================
//
//
//                            // Chiusura applicazione
//                            DropdownMenuItem(
//
//                                leadingIcon = {
//                                    Icon(
//                                        imageVector = Icons.Default.ExitToApp,
//                                        contentDescription = null
//                                    )
//                                },
//
//                                text = {
//                                    Text("Chiudi applicazione")
//                                },
//
//                                onClick = {
//
//                                    // Chiude il menu
//                                    menuOpen = false
//
//                                    // Mostra la finestra di conferma
//                                    showExitDialog = true
//
//                                    // Chiude l'Activity corrente
////                                    activity?.finish()
//                                    // TODO: chiusura app
//                                }
//                            )
//                        }
//                    }
//                }
//            }

                // Corpo Home
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//
//                Text(
//                    text = "Benvenuto in Smart Travel Planner",
//                    fontSize = 26.sp
//                )
            }
        }
    }


    // ===========================
    // Finestra di conferma uscita
    // ===========================

//    if (showExitDialog) {
//
//        AlertDialog(
//            onDismissRequest = {
//                showExitDialog = false
//            },
//
//            title = {
//                Text("Chudere l'app?")
//            },
//
//            text = {
//                Text(
//                    "Sei sicuro di voler chiudere Smart Travel Planner?"
//                )
//            },
//
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        showExitDialog = false
//
//                        // Chiude l'app
//                        activity?.finish()
//                    }
//                ) {
//                    Text("Chiudi")
//                }
//            },
//            dismissButton = {
//                TextButton(
//                    onClick = {
//                        showExitDialog = false
//                    }
//                ) {
//                    Text("Annulla")
//                }
//            }
//        )
//    }
//}
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Image(
//                painter = painterResource(
//                    id = R.drawable.google_maps_image
//                ),
//                contentDescription = "Mappa",
//                modifier = Modifier.size(180.dp)
//            )
//
//            Spacer(
//                modifier = Modifier.height(20.dp)
//            )
//
//            Text(
//                text = "Benvenuto in Smart Travel Planner!",
//                fontSize = 32.sp
//            )
//        }
//    }
//}
//class HomeScreen {
//}
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {

    MaterialTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}