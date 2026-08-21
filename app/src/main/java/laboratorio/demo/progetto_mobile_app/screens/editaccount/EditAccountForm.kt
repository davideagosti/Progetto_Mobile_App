package laboratorio.demo.progetto_mobile_app.screens.editaccount

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import laboratorio.demo.progetto_mobile_app.components.PasswordTextField

@Composable
fun EditAccountForm(

    navController: NavController,

    nome: String,
    cognome: String,

    vecchiaPassword: String,
    nuovaPassword: String,
    confermaNuovaPassword: String,

    accountErrorMessage: String,
    accountSuccessMessage: String,

    passwordErrorMessage : String,
    passwordSuccessMessage : String,

    isLoading: Boolean,

    onNomeChange: (String) -> Unit,
    onCognomeChange: (String) -> Unit,

    onVecchiaPasswordChange: (String) -> Unit,
    onNuovaPasswordChange: (String) -> Unit,
    onConfermaNuovaPasswordChange: (String) -> Unit,

    onSaveClick: () -> Unit,
    onChangePasswordClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp)
            .wrapContentHeight(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F2F2)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)

        ) {

            // ==================================
            // TITOLO
            // ==================================

            Text(

                text = "Modifica account",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold
            )


            // ==================================
            // NOME
            // ==================================

            OutlinedTextField(

                value = nome,

                onValueChange = onNomeChange,

                label = {
                    Text("Nome")
                },

                modifier = Modifier.fillMaxWidth(),

                enabled = !isLoading,

                singleLine = true
            )


            // ==================================
            // COGNOME
            // ==================================

            OutlinedTextField(

                value = cognome,

                onValueChange = onCognomeChange,

                label = {
                    Text("Cognome")
                },

                modifier = Modifier.fillMaxWidth(),

                enabled = !isLoading,

                singleLine = true
            )


            // ==================================
            // ERRORE
            // ==================================

            if (accountErrorMessage.isNotEmpty()) {

                Text(

                    text = accountErrorMessage,

                    color =
                        MaterialTheme
                            .colorScheme
                            .error,

                    fontSize = 14.sp
                )
            }


            // ==================================
            // SUCCESSO
            // ==================================

            if (accountSuccessMessage.isNotEmpty()) {

                Text(

                    text = accountSuccessMessage,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    fontSize = 14.sp
                )
            }


            // ==================================
            // SALVA
            // ==================================

            Button(

                onClick = onSaveClick,

                modifier = Modifier.fillMaxWidth(),

                enabled = !isLoading

            ) {

                if (isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )

                } else {

                    Text("Salva modifiche")
                }
            }

            // ==================================
            // SEPARATORE
            // ==================================

            HorizontalDivider(
                modifier = Modifier.padding(
                    vertical = 8.dp
                )
            )


            // ==================================
            // CAMBIO PASSWORD
            // ==================================

            Text(
                text = "Cambio password",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text =
                "Per sicurezza, inserisci la password attuale prima di scegliere quella nuova.",
                fontSize = 14.sp,
                color = Color.Gray
            )


            // PASSWORD ATTUALE

            PasswordTextField(
                value = vecchiaPassword,

                onValueChange = onVecchiaPasswordChange,

                label = "Password attuale",

                modifier = Modifier.fillMaxWidth()
            )


            // NUOVA PASSWORD

            PasswordTextField(
                value = nuovaPassword,

                onValueChange = onNuovaPasswordChange,

                label = "Nuova password",

                modifier = Modifier.fillMaxWidth()
            )


            // CONFERMA NUOVA PASSWORD

            PasswordTextField(
                value = confermaNuovaPassword,

                onValueChange = onConfermaNuovaPasswordChange,

                label = "Conferma nuova password",

                modifier = Modifier.fillMaxWidth()
            )


            // CAMBIA PASSWORD

            Button(

                onClick = onChangePasswordClick,

                modifier = Modifier.fillMaxWidth(),

                enabled = !isLoading

            ) {

                Text("Cambia password")
            }


            // ==================================
            // MESSAGGI
            // ==================================

            if (passwordErrorMessage.isNotEmpty()) {

                Text(
                    text = passwordErrorMessage,

                    color =
                        MaterialTheme
                            .colorScheme
                            .error,

                    fontSize = 14.sp
                )
            }

            if (passwordSuccessMessage.isNotEmpty()) {

                Text(
                    text = passwordSuccessMessage,

                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,

                    fontSize = 14.sp
                )
            }
        }
    }
}