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

@Composable
fun AccountMenu( navController: NavController) {
//fun AccountMenu () {


    var menuOpen by remember {
        mutableStateOf(false)
    }


    var showExitDialog by remember {
        mutableStateOf(false)
    }


    val activity =
        LocalContext.current as? Activity


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
//        if(menuOpen){


            DropdownMenu(

                // Il menu è già visibile perché creato dentro l'if
                expanded = menuOpen,

                // Chiusura del menu cliccando fuori
                onDismissRequest = {
                    menuOpen = false
                },

                // Dimensione del riquadro menu
                modifier = Modifier.width(220.dp)

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


                // Opzione registrazione nuovo utente
                DropdownMenuItem(

                    leadingIcon = {

                        Icon(
                            Icons.Default.PersonAdd,
                            null
                        )

                    },

                    text={
                        Text("Registrati")
                    },

                    onClick={
                        menuOpen = false
                        navController.navigate(Routes.Register.route)
                        // TODO: apertura pagina registrazione
                        //navController.navigate("register")
                    }
                )


                // Opzione accesso utente esistente
                DropdownMenuItem(

                    leadingIcon = {

                        Icon(
                            Icons.Default.Login,
                            null
                        )

                    },

                    text={
                        Text("Accedi")
                    },

                    onClick={
                        menuOpen = false
                        navController.navigate(Routes.Login.route)
                        // TODO: apertura pagina login
                        //navController.navigate("login")
                    }
                )


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

                    text={
                        Text("Modifica account")
                    },

                    onClick={
                        menuOpen=false
                        // TODO: modifica nome/password
                    }
                )


                // Disconnessione account
                DropdownMenuItem(

                    leadingIcon = {

                        Icon(
                            Icons.Default.Logout,
                            null
                        )

                    },

                    text={
                        Text("Disconnetti")
                    },

                    onClick={
                        menuOpen=false
                        // TODO: logout account
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
                            null
                        )

                    },


                    text={
                        Text("Chiudi applicazione")
                    },


                    onClick={

                        // Chiude il menu
                        menuOpen=false

                        // Mostra la finestra di conferma
                        showExitDialog=true

                        // Chiude l'Activity corrente
                        // activity?.finish()
                        // TODO: chiusura app

                    }
                )

            }
//        }
    }

    // ===========================
    // Finestra di conferma uscita
    // ===========================

    if(showExitDialog){


        AlertDialog(

            onDismissRequest={
                showExitDialog=false
            },


            title={
                Text("Chiudere l'app?")
            },


            text={
                Text(
                    "Sei sicuro di voler chiudere Smart Travel Planner?"
                )
            },


            confirmButton={

                TextButton(

                    onClick={

                        showExitDialog=false

                        // Chiude l'app
                        activity?.finish()

                    }

                ){

                    Text("Chiudi")

                }
            },


            dismissButton={

                TextButton(

                    onClick={
                        showExitDialog=false
                    }

                ){

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