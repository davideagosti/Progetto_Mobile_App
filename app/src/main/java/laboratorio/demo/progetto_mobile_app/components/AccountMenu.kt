package laboratorio.demo.progetto_mobile_app.components

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import laboratorio.demo.progetto_mobile_app.R
import androidx.navigation.NavController
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import androidx.compose.material3.Divider
import androidx.navigation.compose.rememberNavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AccountMenu( navController: NavController) {

    // ==========================================
    // STATO MENU
    // ==========================================

    var menuOpen by remember {
        mutableStateOf(false)
    }

    // Controlla la visualizzazione della finestra di conferma
    var showExitDialog by remember {
        mutableStateOf(false)
    }

    // Stato del popup di logout
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    // ==========================================
    // ACTIVITY
    // ==========================================

    // Recupera l'Activity corrente
    val activity =
        LocalContext.current as? Activity

    // ==========================================
    // FIREBASE
    // ==========================================

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val firestore = remember {
        FirebaseFirestore.getInstance()
    }


    // ==========================================
    // UTENTE AUTENTICATO
    // ==========================================

    val currentUser = auth.currentUser

    // ==========================================
    // DATI UTENTE
    // ==========================================

    var nome by remember {
        mutableStateOf("")
    }

    var cognome by remember {
        mutableStateOf("")
    }


    // ==========================================
    // RECUPERO DATI DA FIRESTORE
    // ==========================================

    LaunchedEffect(currentUser?.uid) {

        if (currentUser != null) {

            firestore
                .collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->

                    if (document.exists()) {

                        nome =
                            document.getString("nome")
                                ?: ""

                        cognome =
                            document.getString("cognome")
                                ?: ""
                    }
                }
        }
    }


    // ==========================================
    // ACCOUNT MENU
    // ==========================================


    // Contenitore dell'icona account e del suo menu
    Box {

        // Icona account
        IconButton(
            // Apre il menu account
            onClick = {
                menuOpen = true
            }
        ) {


            Image(

                painter = painterResource(
                    id = R.drawable.avatar_account_guest
                ),

                contentDescription = "Account",

                modifier = Modifier
                    .size(48.dp)
                    .shadow(
                        4.dp,
                        CircleShape
                    )
                    .clip(
                        CircleShape
                    )
                    .border(
                        2.dp,
                        Color.LightGray,
                        CircleShape
                    )
            )

        }


        // Il menu viene creato solo quando viene aperto
        // Evita rallentamenti all'avvio dell'app
        DropdownMenu(

            // Il menu è già visibile perché creato dentro l'if
            expanded = menuOpen,

            // Chiusura del menu cliccando fuori
            onDismissRequest = {
                menuOpen = false
            },

            // Dimensione del riquadro menu
            modifier = Modifier.width(260.dp)

        ){

            // ===========================
            // SEZIONE ACCESSO ACCOUNT
            // ===========================

            Text(
                text="Account",
                fontSize=13.sp,
                color=Color.Gray,
                modifier=
                Modifier.padding(
                    16.dp
                )
            )

            if (currentUser != null) {

                // ==================================
                // NOME E COGNOME
                // ==================================

                Text(
                    text = if (
                        nome.isNotBlank() ||
                        cognome.isNotBlank()
                    ) {
                        "$nome $cognome"
                    } else {
                        "Utente"
                    },

                    fontSize = 17.sp,

                    style = MaterialTheme.typography.titleMedium,

                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp
                    )
                )


                // ==================================
                // EMAIL
                // ==================================

                Text(
                    text = currentUser.email
                        ?: "Email non disponibile",

                    fontSize = 13.sp,

                    color = Color.Gray,

                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 2.dp,
                        bottom = 12.dp
                    )
                )


            } else {

                // ==================================
                // UTENTE NON AUTENTICATO
                // ==================================

                Text(
                    text = "Nessun account autenticato",

                    fontSize = 14.sp,

                    color = Color.Gray,

                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = 12.dp
                    )
                )
            }

            // ==================================
            // ACCESSO / REGISTRAZIONE
            // ==================================

            if (currentUser == null) {

                // Opzione registrazione nuovo utente
                DropdownMenuItem(

                    leadingIcon = {

                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = null
                        )
                    },

                    text = {
                        Text("Registrati")
                    },

                    onClick = {
                        menuOpen = false
                        navController.navigate(Routes.Register.route)
                        // TODO: apertura pagina registrazione
                    }
                )


                // Opzione accesso utente esistente
                DropdownMenuItem(

                    leadingIcon = {

                        Icon(
                            Icons.Default.Login,
                            contentDescription = null
                        )
                    },

                    text = {
                        Text("Accedi")
                    },

                    onClick = {
                        menuOpen = false
                        navController.navigate(Routes.Login.route)
                        // TODO: apertura pagina login
                    }
                )
            }

            // Separatore tra accesso e gestione account
            Divider(
                thickness = 2.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(
                    vertical = 8.dp
                )
            )

            // ===========================
            // SEZIONE GESTIONE ACCOUNT
            // ===========================

            Text(
                text = "Gestione account",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )

            // Modifica dati account
            DropdownMenuItem(

                leadingIcon = {

                    Icon(
                        Icons.Default.ManageAccounts,
                        null
                    )
                },

                text = {
                    Text("Modifica account")
                },

                onClick = {
                    menuOpen=false
                    // TODO: modifica nome/password

                    navController.navigate(
                        Routes.EditAccount.route
                    )
                }
            )


            // Disconnessione account
            DropdownMenuItem(

                leadingIcon = {

                    Icon(
                        Icons.Default.Logout,
                        contentDescription = null
                    )
                },

                text = {
                    Text("Disconnetti")
                },

                onClick = {
                    menuOpen = false
                    // TODO: logout account

                    // Mostra conferma logout
                    showLogoutDialog = true
                    // FirebaseAuth.getInstance().signOut()
                }
            )



            Divider(
                thickness = 2.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(
                    vertical = 8.dp
                )
            )

            // ===========================
            // SEZIONE APPLICAZIONE
            // ===========================

            // Chiusura applicazione
            DropdownMenuItem(

                leadingIcon = {

                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = null
                    )
                },


                text = {
                    Text("Chiudi applicazione")
                },


                onClick = {

                    // Chiude il menu
                    menuOpen = false

                    // Mostra la finestra di conferma
                    showExitDialog = true

                    // Chiude l'Activity corrente
                    // activity?.finish()
                    // TODO: chiusura app
                }
            )
        }
    }

    // ===========================
    // Finestra di conferma uscita
    // ===========================

    if (showLogoutDialog) {

        AlertDialog(

            onDismissRequest = {
                showLogoutDialog = false
            },

            title = {
                Text("Disconnettere l'account?")
            },

            text = {
                Text(
                    "Sei sicuro di voler disconnettere il tuo account?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        // Chiude il popup
                        showLogoutDialog = false

                        // Logout Firebase
                        FirebaseAuth
                            .getInstance()
                            .signOut()

                        // Torna alla schermata Login
                        navController.navigate(
                            Routes.Login.route
                        ) {

                            // Rimuove le schermate precedenti
                            popUpTo(
                                Routes.Home.route
                            ) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }

                ) {

                    Text("Disconnetti")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {
                        showLogoutDialog = false
                    }

                ) {

                    Text("Annulla")
                }
            }
        )
    }

    if(showExitDialog) {

        AlertDialog (

            onDismissRequest = {
                showExitDialog = false
            },

            title = {
                Text("Chiudere l'app?")
            },

            text = {
                Text(
                    "Sei sicuro di voler chiudere Smart Travel Planner?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        showExitDialog = false

                        // Chiude l'app
                        activity?.finish()
                    }
                ) {

                    Text("Chiudi")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {
                        showExitDialog = false
                    }

                ) {

                    Text("Annulla")

                }

            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun AccountMenuPreview() {

    MaterialTheme {
        AccountMenu(
            navController = rememberNavController()
        )
    }
}