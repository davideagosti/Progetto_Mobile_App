package laboratorio.demo.progetto_mobile_app.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

import laboratorio.demo.progetto_mobile_app.navigation.AppNavigation
import laboratorio.demo.progetto_mobile_app.screens.splash.SplashScreen
import laboratorio.demo.progetto_mobile_app.ui.theme.Progetto_Mobile_AppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //installSplashScreen()

        //val splashScreen = installSplashScreen()

        //var keepSplash = true
/*
        splashScreen.setKeepOnScreenCondition {
            keepSplash
        }

        lifecycleScope.launch {

            delay(3000)

            keepSplash = false
        }*/

        super.onCreate(savedInstanceState)

        setContent {
            Progetto_Mobile_AppTheme {

                val navController = rememberNavController()

                //AppNavigation(navController)

                var showSplash by rememberSaveable {
                    mutableStateOf(true)
                }


                LaunchedEffect(Unit) {

                    delay(3000)

                    showSplash = false
                }


                if(showSplash) {

                    SplashScreen()

                } else {

                    AppNavigation(navController)

                }

//                if (loading) {
//
//                    AppScaffold(
//
//                        showTopBar = true,
//                        showBottomBar = true,
//
//                        //topBarTitle = "Smart Travel Planner",
//                        //bottomBarText = "Benvenuto!"
//
//                    ) { innerPadding ->
//
//                        SplashScreen(innerPadding)
//
//                    }
//
//                } else {
//
//                    AppNavigation(navController)
//
//                }

                //AppNavigation()

//                var loading by rememberSaveable  {
//                    mutableStateOf(true)
//                }
//
//                LaunchedEffect(Unit) {
//                    if(loading) {
//                        delay(3000)   // Attende 3 secondi
//                        loading = false
//                    }
//                }
//
//                if(loading) {
//                    SplashScreen()
//                } else {
//                    AppNavigation(navController)
//                }

                /*
                AppScaffold (

                    showTopBar = !loading,

                    showBottomBar = !loading,

                    topBarTitle = if(loading)
                        ""
                    else "Smart Travel Planner"

                ) { innerPadding ->

                    Surface (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)

                    ) {
                        if(loading){
                            SplashScreen()
                        } else {
                            AppNavigation( navController )
                        }
                    }
                }*/

                /*
                if (loading) {

                    // ===========================
                    // SPLASH SCREEN
                    // ===========================

                    AppScaffold(
                        showTopBar = true,
                        showBottomBar = true,

                        topBarTitle = "",
                        bottomBarText = ""

                    ) { innerPadding ->

                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            //color = MaterialTheme.colorScheme.background
                        ) {
                            //Greeting("Android")

                            //var loading by remember { mutableStateOf(true) }


                            SplashScreen()
                        }
                    }
                } else {

                    // ===========================
                    // HOME SCREEN
                    // ===========================

                    AppScaffold(

                        showTopBar = true,

                        showBottomBar = true,

                        //topBarTitle = "Smart Travel Planner",

                        //bottomBarText = "Benvenuto!"

                    ) { innerPadding ->


                        Surface(

                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)

                        ) {
                            //Text("Test")
                            //Greeting("Android")
                            //HomeScreen()
                            AppNavigation()
                        }
                    }
                }*/
            }
        }
    }
}


//    @Composable
//    fun Greeting(name: String, modifier: Modifier = Modifier) {
//        Box(
//            modifier = modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//            Image(
//                painter = painterResource(id = R.drawable.google_maps_image),
//                contentDescription = "Logo",
//                modifier = Modifier.size(180.dp)
//            )
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Text(
//                    //text = "Hello $name!",
//                    text = "Benvenuto in Smart Travel Planner!",
//                    fontSize = 32.sp,
//                    modifier = modifier
//                )
//            }
//        }
//    }
//
//    @Composable
//    fun LoadingScreen() {
//
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//                CircularProgressIndicator(
//                    color = colorResource(id = R.color.green)
//                )
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Text(
//                    text = "Caricamento...",
//                    fontSize = 22.sp
//                )
//            }
//        }
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun GreetingPreview() {
//        Progetto_Mobile_AppTheme {
//            //Greeting("Android")
//            HomeScreen()
//        }
//    }
//}