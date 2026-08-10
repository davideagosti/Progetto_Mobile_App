package laboratorio.demo.progetto_mobile_app.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import laboratorio.demo.progetto_mobile_app.R
import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.components.AppBarConfig
import laboratorio.demo.progetto_mobile_app.ui.theme.Progetto_Mobile_AppTheme

@Composable
//@Preview
fun SplashScreen(
    //innerPadding: PaddingValues = PaddingValues(0.dp)
    title: String = "Smart Travel Planner",
    //welcomeText: String = "Benvenuto!",
    loadingText: String = "Caricamento...",
    logo: Int = R.drawable.google_maps_image,
    loadingColor: Color = colorResource(R.color.green)
) {

    val green = colorResource(R.color.green)

    AppScaffold(

        showTopBar = true,

        showBottomBar = true,

        //topBarTitle = title,
        topBarTitle = "",

        //bottomBarText = welcomeText,
        bottomBarText = "",

        //topBarColor = green,
        appBarConfig = AppBarConfig(
            backgroundColor = green
        ),

        bottomBarColor = green

        //topBarTitle = "Smart Travel Planner",

        //bottomBarText = "Benvenuto!"

    ) { padding ->


        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            contentAlignment = Alignment.Center

        ) {


            Column(

                horizontalAlignment = Alignment.CenterHorizontally

            ) {


                Image(

//                    painter = painterResource(
//                        R.drawable.google_maps_image
//                    ),
                    painter = painterResource(logo),

                    contentDescription = null,

                    modifier = Modifier.size(180.dp)

                )


                Spacer(
                    Modifier.height(20.dp)
                )


                Text(

                    //text = "Smart Travel Planner",
                    text = title,

                    fontSize = 32.sp,

                    fontWeight = FontWeight.Bold

                )


                Spacer(
                    Modifier.height(10.dp)
                )


                Text(
                    text = "Benvenuto!",
                    //text = welcomeText,
                    fontSize = 22.sp
                )


                Spacer(
                    Modifier.height(40.dp)
                )


                CircularProgressIndicator(

                    color = loadingColor
//                    color = colorResource(
//                        R.color.green
//                    )

                )


                Spacer(
                    Modifier.height(16.dp)
                )


                Text(
                    //text = "Caricamento...",
                    text = loadingText,
                    color = colorResource(
                        R.color.gray
                    )
                )
            }
        }
    }

/*
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Logo dell'app
            Image(
                painter = painterResource(id = R.drawable.google_maps_image),
                contentDescription = "Logo Smart Travel Planner",
                modifier = Modifier.size(180.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Nome dell'app
            Text(
                text = "Smart Travel Planner",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Messaggio di benvenuto
            Text(
                text = "Benvenuto!",
                fontSize = 22.sp
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            // Indicatore di caricamento
            CircularProgressIndicator(
                color = colorResource(id = R.color.green)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Caricamento...",
                color = colorResource(id = R.color.gray),
                fontSize = 18.sp
            )
        }
    }*/
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SplashScreenPreview() {
    Progetto_Mobile_AppTheme {

        SplashScreen()
    }
}

//class SplashScreen {
//}