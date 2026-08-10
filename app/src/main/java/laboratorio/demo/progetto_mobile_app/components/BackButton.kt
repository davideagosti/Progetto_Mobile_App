package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun BackButton(
//    navController: NavController,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        modifier = modifier,
        onClick = onClick
//        {
////            navController.popBackStack()
//            navController.navigate(Routes.Home) {
//                inclusive = false
//            }

//            launchSingleTop = true
//        }
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Indietro",
            modifier = Modifier.size(32.dp)
        )

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BackButtonPreview() {
    MaterialTheme {
        BackButton(
//            navController = rememberNavController()
            onClick = {}
        )
    }
}